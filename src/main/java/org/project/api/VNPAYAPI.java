// src/main/java/org/project/controller/VNPAYAPI.java
package org.project.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.project.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Controller này xử lý tất cả các API liên quan đến thanh toán, nạp và rút tiền
 * sử dụng VNPAY Sandbox và các chức năng ví nội bộ.
 * Nó cũng phục vụ các trang HTML liên quan đến thanh toán.
 */
@Controller
@RequestMapping("/api/payment") // Đặt base path là /api/payment
public class VNPAYAPI {

    @Autowired
    private WalletService walletService; // Sử dụng interface WalletService

    /**
     * Endpoint để hiển thị trang form nạp tiền.
     * Có thể truy cập qua http://localhost:8089/api/payment/form
     *
     * @return Tên view (template) để hiển thị.
     */
    @GetMapping("/form")
    public String showPaymentForm() {
        return "frontend/payment_form"; // Trả về template payment_form.html trong thư mục frontend
    }

    /**
     * Endpoint để khởi tạo yêu cầu nạp tiền vào ví qua VNPAY.
     * Hệ thống sẽ tạo một giao dịch thanh toán và trả về URL VNPAY để người dùng chuyển hướng.
     *
     * @param userId  ID của người dùng thực hiện nạp tiền
     * @param amount  Số tiền muốn nạp
     * @param request HttpServletRequest để lấy thông tin IP của client
     * @return ResponseEntity chứa URL thanh toán VNPAY hoặc thông báo lỗi
     */
    @PostMapping("/deposit/vnpay/initiate")
    @ResponseBody // Giữ @ResponseBody cho phương thức này vì nó trả về JSON
    public ResponseEntity<?> initiateVnpayDeposit(@RequestParam Long userId,
                                                  @RequestParam BigDecimal amount,
                                                  HttpServletRequest request) {
        try {
            String paymentUrl = walletService.initiateVnpayDeposit(userId, amount, request);
            return ResponseEntity.ok(Map.of("paymentUrl", paymentUrl));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Error initiating VNPAY deposit: " + e.getMessage()));
        }
    }

    /**
     * Endpoint callback từ VNPAY sau khi người dùng hoàn tất thanh toán.
     * VNPAY sẽ gửi các tham số kết quả về đây. Hệ thống sẽ xác thực và cập nhật trạng thái giao dịch.
     * Phương thức này sẽ trực tiếp trả về tên view (HTML template) và truyền dữ liệu qua Model.
     *
     * @param request HttpServletRequest chứa các tham số VNPAY
     * @param model   Đối tượng Model để truyền dữ liệu sang view
     * @return Tên view (template) để hiển thị kết quả
     */
    @RequestMapping(value = "/deposit/vnpay/callback", method = {RequestMethod.GET, RequestMethod.POST})
    public String handleVnpayCallback(HttpServletRequest request, Model model) {
        // Gọi service để xử lý logic chính và xác thực hash
        boolean success = walletService.handleVnpayCallback(request);

        // Lấy thông tin cần thiết để truyền sang trang thành công/thất bại
        String vnp_TxnRef = request.getParameter("vnp_TxnRef");
        BigDecimal vnp_Amount = null;
        String vnp_Amount_str = request.getParameter("vnp_Amount"); // Lấy giá trị chuỗi

        // --- Bắt đầu phần debug chi tiết ---
        System.out.println("DEBUG in VNPAYAPI.handleVnpayCallback:");
        System.out.println("  Raw vnp_Amount_str from request: '" + vnp_Amount_str + "'");
        // --- Kết thúc phần debug chi tiết ---

        if (vnp_Amount_str != null && !vnp_Amount_str.isEmpty()) {
            System.out.println("  vnp_Amount_str is not null or empty. Attempting BigDecimal conversion.");
            try {
                // Thêm kiểm tra null tường minh ngay trước khi tạo BigDecimal
                // Điều này là để phòng ngừa các trường hợp cực kỳ hiếm gặp hoặc môi trường đặc biệt
                if (vnp_Amount_str != null) {
                    vnp_Amount = new BigDecimal(vnp_Amount_str).divide(BigDecimal.valueOf(100));
                    System.out.println("  Successfully parsed vnp_Amount: " + vnp_Amount);
                } else {
                    // Trường hợp này không nên xảy ra nếu logic if bên ngoài đúng
                    System.err.println("  ERROR: vnp_Amount_str became null unexpectedly before BigDecimal conversion.");
                }
            } catch (NumberFormatException e) {
                System.err.println("  Error parsing VNPAY amount: '" + vnp_Amount_str + "'. " + e.getMessage());
                // Nếu parse thất bại, vnp_Amount vẫn là null, sẽ được xử lý ở view
            }
        } else {
            System.out.println("  vnp_Amount_str is null or empty. BigDecimal conversion skipped.");
        }

        String vnp_TransactionNo = request.getParameter("vnp_TransactionNo");
        String vnp_ResponseCode = request.getParameter("vnp_ResponseCode");

        if (success) {
            model.addAttribute("txnRef", vnp_TxnRef);
            model.addAttribute("amount", vnp_Amount); // vnp_Amount có thể là null nếu không parse được
            model.addAttribute("vnpayTransactionNo", vnp_TransactionNo);
            return "frontend/success"; // Trả về template success.html trong thư mục frontend
        } else {
            String message = "Giao dịch VNPAY thất bại. Mã phản hồi: " + (vnp_ResponseCode != null ? vnp_ResponseCode : "N/A");
            if (vnp_TxnRef != null) {
                message += ". Mã giao dịch của bạn: " + vnp_TxnRef;
            }
            model.addAttribute("message", message);
            return "frontend/error"; // Trả về template error.html trong thư mục frontend
        }
    }

    /**
     * Endpoint để thực hiện rút tiền từ ví của người dùng.
     * Chức năng này chỉ xử lý logic nội bộ (trừ số dư ví) và không tích hợp với cổng thanh toán bên ngoài.
     *
     * @param userId ID của người dùng thực hiện rút tiền
     * @param amount Số tiền muốn rút
     * @return ResponseEntity chứa thông báo kết quả rút tiền
     */
    @PostMapping("/withdraw")
    @ResponseBody // Giữ @ResponseBody cho phương thức này vì nó trả về JSON
    public ResponseEntity<?> withdrawMoney(@RequestParam Long userId,
                                           @RequestParam BigDecimal amount) {
        try {
            boolean success = walletService.withdrawFromWallet(userId, amount);
            if (success) {
                return ResponseEntity.ok(Map.of("message", "Withdrawal successful."));
            } else {
                return ResponseEntity.badRequest().body(Map.of("message", "Insufficient balance or invalid amount."));
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Error processing withdrawal: " + e.getMessage()));
        }
    }
}
