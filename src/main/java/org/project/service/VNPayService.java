// src/main/java/org/project/service/VNPayService.java
package org.project.service;

import jakarta.servlet.http.HttpServletRequest;
import org.project.config.VNPayConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class VNPayService {

    @Autowired
    private VNPayConfig vnpayConfig; // Sử dụng VNPayConfig đã được @Component

    // Loại bỏ calculateTotalAmount vì không liên quan đến nạp tiền vào ví
    // public BigDecimal calculateTotalAmount(Long userId) {
    //     List<CartItemEntity> cartItems = cartItemRepository.findByUserEntityId(userId);
    //     BigDecimal totalAmount = BigDecimal.ZERO;
    //
    //     for (CartItemEntity item : cartItems) {
    //         BigDecimal productPrice = item.getProductEntity().getPrice();
    //         BigDecimal quantity = BigDecimal.valueOf(item.getQuantity());
    //         BigDecimal itemTotal = productPrice.multiply(quantity);
    //         totalAmount = totalAmount.add(itemTotal);
    //     }
    //
    //     return totalAmount;
    // }

    /**
     * Tạo URL thanh toán VNPAY cho giao dịch nạp tiền vào ví.
     *
     * @param txnRef      Mã giao dịch của hệ thống bạn (duy nhất)
     * @param orderInfo   Thông tin mô tả đơn hàng/giao dịch
     * @param totalAmount Tổng số tiền cần thanh toán (BigDecimal, sẽ được nhân 100)
     * @param request     HttpServletRequest để lấy IP của client
     * @return URL chuyển hướng đến cổng thanh toán VNPAY
     */
    public String createPaymentUrl(String txnRef, String orderInfo, BigDecimal totalAmount, HttpServletRequest request) {
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String vnp_TmnCode = vnpayConfig.getTmnCode();
        String orderType = "billpayment"; // Loại hình thanh toán cho nạp tiền

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        // Chuyển sang VND và nhân với 100 (VNPAY yêu cầu số nguyên)
        vnp_Params.put("vnp_Amount", String.valueOf(totalAmount.multiply(BigDecimal.valueOf(100)).longValue()));
        vnp_Params.put("vnp_CurrCode", "VND");

        vnp_Params.put("vnp_TxnRef", txnRef); // Mã giao dịch của bạn
        vnp_Params.put("vnp_OrderInfo", orderInfo); // Mô tả giao dịch
        vnp_Params.put("vnp_OrderType", orderType);

        String locate = "vn"; // Ngôn ngữ hiển thị trên cổng VNPAY
        vnp_Params.put("vnp_Locale", locate);

        vnp_Params.put("vnp_ReturnUrl", vnpayConfig.getReturnUrl()); // URL callback của bạn
        vnp_Params.put("vnp_IpAddr", vnpayConfig.getIpAddress(request)); // IP của client

        // Các tham số thời gian
        vnp_Params.put("vnp_CreateDate", vnpayConfig.getVnpayCreateDate());

        // Thời gian hết hạn giao dịch (ví dụ: 15 phút)
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
        cld.add(Calendar.MINUTE, 15);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        // Sắp xếp các tham số và tạo chuỗi hash
        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        for (String fieldName : fieldNames) {
            String fieldValue = vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                if (fieldNames.indexOf(fieldName) < fieldNames.size() - 1) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = vnpayConfig.hmacSHA512(vnpayConfig.getHashSecret(), hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = vnpayConfig.getVnpayUrl() + "?" + queryUrl;
        return paymentUrl;
    }

    /**
     * Xác thực kết quả trả về từ VNPAY (callback).
     *
     * @param request HttpServletRequest chứa các tham số từ VNPAY
     * @return true nếu chữ ký hợp lệ và giao dịch thành công, false nếu không.
     */
    public boolean validateVnpayCallback(HttpServletRequest request) {
        Map<String, String> fields = new HashMap<>();
        for (Enumeration<String> params = request.getParameterNames(); params.hasMoreElements(); ) {
            String fieldName = params.nextElement();
            String fieldValue = request.getParameter(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                fields.put(fieldName, fieldValue);
            }
        }

        String vnp_SecureHash = fields.get("vnp_SecureHash");
        fields.remove("vnp_SecureHashType"); // Loại bỏ tham số này nếu có
        fields.remove("vnp_SecureHash"); // Loại bỏ SecureHash để tính toán lại

        // Sắp xếp các tham số và tạo chuỗi hash để xác thực
        List<String> fieldNames = new ArrayList<>(fields.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        for (String fieldName : fieldNames) {
            String fieldValue = fields.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                if (fieldNames.indexOf(fieldName) < fieldNames.size() - 1) {
                    hashData.append('&');
                }
            }
        }

        String calculatedSecureHash = vnpayConfig.hmacSHA512(vnpayConfig.getHashSecret(), hashData.toString());

        // So sánh chữ ký nhận được với chữ ký tự tính toán
        return calculatedSecureHash.equals(vnp_SecureHash);
    }
}
