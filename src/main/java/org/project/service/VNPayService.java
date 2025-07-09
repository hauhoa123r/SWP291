// src/main/java/org/project/service/VNPayService.java
package org.project.service;

import jakarta.servlet.http.HttpServletRequest;
import org.project.config.VNPayConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class VNPayService {

    @Autowired
    private VNPayConfig vnpayConfig; // Sử dụng VNPayConfig đã được @Component

    /**
     * Tạo URL thanh toán VNPAY cho giao dịch nạp tiền vào ví.
     *
     * @param txnRef    Mã giao dịch duy nhất của hệ thống bạn
     * @param orderInfo Thông tin đơn hàng/giao dịch
     * @param amount    Số tiền cần thanh toán
     * @param request   HttpServletRequest để lấy IP của client
     * @return URL thanh toán VNPAY
     */
    public String createPaymentUrl(String txnRef, String orderInfo, BigDecimal amount, HttpServletRequest request) {
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String vnp_TmnCode = vnpayConfig.getTmnCode();
        String vnp_Amount = String.valueOf(amount.multiply(BigDecimal.valueOf(100)).intValue()); // Số tiền VNPAY yêu cầu là số nguyên (đã nhân 100)
        String vnp_CurrCode = "VND";
        String vnp_TxnRef = txnRef; // Mã giao dịch của hệ thống bạn
        String vnp_OrderInfo = orderInfo;
        String vnp_OrderType = "other"; // Loại giao dịch (có thể là "billpayment", "fashion", "other",...)
        String vnp_Locale = "vn"; // Ngôn ngữ hiển thị trên cổng VNPAY (vn/en)
        String vnp_ReturnUrl = vnpayConfig.getReturnUrl();
        String vnp_IpAddr = vnpayConfig.getIpAddress(request);
        String vnp_CreateDate = vnpayConfig.getVnpayCreateDate();

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", vnp_Amount);
        vnp_Params.put("vnp_CurrCode", vnp_CurrCode);
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
        vnp_Params.put("vnp_OrderType", vnp_OrderType);
        vnp_Params.put("vnp_Locale", vnp_Locale);
        vnp_Params.put("vnp_ReturnUrl", vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        // Build URL
        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator<String> itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = itr.next();
            String fieldValue = vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                // Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                // Build query url
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = vnpayConfig.hmacSHA512(vnpayConfig.getHashSecret(), hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        return vnpayConfig.getVnpayUrl() + "?" + queryUrl;
    }

    /**
     * Xác thực chữ ký điện tử từ callback của VNPAY.
     *
     * @param request HttpServletRequest chứa các tham số VNPAY
     * @return true nếu chữ ký hợp lệ, false nếu không hợp lệ
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

        // --- Bắt đầu phần log để debug ---
        System.out.println("VNPAY Callback - Data for Hashing: " + hashData);
        String calculatedSecureHash = vnpayConfig.hmacSHA512(vnpayConfig.getHashSecret(), hashData.toString());
        System.out.println("VNPAY Callback - Calculated Hash: " + calculatedSecureHash);
        System.out.println("VNPAY Callback - Received Hash: " + vnp_SecureHash);
        // --- Kết thúc phần log để debug ---

        // So sánh chữ ký nhận được với chữ ký tự tính toán
        return calculatedSecureHash.equals(vnp_SecureHash);
    }
}
