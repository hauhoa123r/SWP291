// src/main/java/org/project/config/VNPayConfig.java
package org.project.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Lớp cấu hình VNPAY, chứa các thông tin cần thiết cho việc tích hợp VNPAY
 * và các phương thức tiện ích để tạo chữ ký điện tử, lấy thời gian, IP.
 */
@Component
public class VNPayConfig {

    @Value("${vnpay.tmnCode}")
    private String tmnCode;

    @Value("${vnpay.hashSecret}")
    private String hashSecret;

    @Value("${vnpay.url}")
    private String vnpayUrl;

    @Value("${vnpay.returnUrl}")
    private String returnUrl;

    public String getTmnCode() {
        return tmnCode;
    }

    public String getHashSecret() {
        return hashSecret;
    }

    public String getVnpayUrl() {
        return vnpayUrl;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    /**
     * Hàm tạo chuỗi hash SHA512
     *
     * @param key  Khóa bí mật (HashSecret)
     * @param data Dữ liệu cần hash
     * @return Chuỗi hash SHA512
     */
    public String hmacSHA512(String key, String data) {
        try {
            Mac hmacSha512 = Mac.getInstance("HmacSHA512");
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
            hmacSha512.init(secretKey);
            byte[] hash = hmacSha512.doFinal(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("Failed to calculate HMAC SHA512", e);
        }
    }

    /**
     * Hàm lấy ngày giờ hiện tại theo định dạng VNPAY yêu cầu (yyyyMMddHHmmss)
     *
     * @return Chuỗi ngày giờ hiện tại
     */
    public String getVnpayCreateDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        formatter.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh")); // Đặt múi giờ Việt Nam
        return formatter.format(new Date());
    }

    /**
     * Hàm lấy địa chỉ IP của client từ HttpServletRequest
     *
     * @param request HttpServletRequest
     * @return Địa chỉ IP của client
     */
    public String getIpAddress(jakarta.servlet.http.HttpServletRequest request) {
        String ipAddress;
        try {
            ipAddress = request.getHeader("X-FORWARDED-FOR");
            if (ipAddress == null || "".equals(ipAddress)) {
                ipAddress = request.getRemoteAddr();
            }
        } catch (Exception e) {
            ipAddress = "Unknown"; // Trường hợp không lấy được IP
        }
        return ipAddress;
    }
}
