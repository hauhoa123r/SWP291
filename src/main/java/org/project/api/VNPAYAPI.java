// src/main/java/org/project/controller/VNPAYAPI.java
package org.project.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.project.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Controller này xử lý tất cả các API liên quan đến thanh toán, nạp và rút tiền
 * sử dụng VNPAY Sandbox và các chức năng ví nội bộ.
 */
@RestController
@RequestMapping("/api/payment") // Đặt base path là /api/payment
public class VNPAYAPI {

    @Autowired
    private WalletService walletService; // Sử dụng interface WalletService

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
    public ResponseEntity<?> initiateVnpayDeposit(@RequestParam Long userId,
                                                  @RequestParam BigDecimal amount,
                                                  HttpServletRequest request) {
        try {
            String paymentUrl = walletService.initiateVnpayDeposit(userId, amount, request);
            return ResponseEntity.ok(Map.of("paymentUrl", paymentUrl));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            // Cảnh báo: Lỗi ở đây có thể do ràng buộc NOT NULL của order_id trong PaymentEntity
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Error initiating VNPAY deposit: " + e.getMessage() + ". Vui lòng kiểm tra ràng buộc order_id trong PaymentEntity."));
        }
    }

    /**
     * Endpoint callback từ VNPAY sau khi người dùng hoàn tất thanh toán.
     * VNPAY sẽ gửi các tham số kết quả về đây. Hệ thống sẽ xác thực và cập nhật trạng thái giao dịch.
     *
     * @param request HttpServletRequest chứa các tham số VNPAY
     * @return RedirectView để chuyển hướng người dùng về trang kết quả trên frontend
     */
    @GetMapping("/deposit/vnpay/callback")
    public RedirectView handleVnpayCallback(HttpServletRequest request) {
        boolean success = walletService.handleVnpayCallback(request);
        if (success) {
            // Chuyển hướng về trang thành công trên frontend của bạn
            // Ví dụ: http://localhost:3000/payment-status?status=success
            return new RedirectView("/payment/success?status=success");
        } else {
            // Chuyển hướng về trang thất bại trên frontend của bạn
            // Ví dụ: http://localhost:3000/payment-status?status=fail
            return new RedirectView("/payment/success?status=fail");
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
            // Cảnh báo: Lỗi ở đây có thể do ràng buộc NOT NULL của order_id trong PaymentEntity
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Error processing withdrawal: " + e.getMessage() + ". Vui lòng kiểm tra ràng buộc order_id trong PaymentEntity."));
        }
    }
}
