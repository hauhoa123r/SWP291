// src/main/java/org/project/service/WalletService.java
package org.project.service;

import jakarta.servlet.http.HttpServletRequest;
import org.project.entity.WalletEntity;

import java.math.BigDecimal;

public interface WalletService {

    /**
     * Lấy thông tin ví của người dùng. Nếu chưa có, tạo ví mới.
     *
     * @param userId ID của người dùng
     * @return WalletEntity của người dùng
     */
    WalletEntity getOrCreateWallet(Long userId);

    /**
     * Khởi tạo giao dịch nạp tiền vào ví thông qua VNPAY.
     * Tạo một bản ghi Payment với trạng thái PENDING và trả về URL VNPAY.
     * <p>
     * LƯU Ý QUAN TRỌNG: Với cấu trúc PaymentEntity hiện tại của bạn (orderEntity là @NotNull và nullable = false),
     * việc lưu PaymentEntity mà không gán OrderEntity sẽ gây lỗi SQL/JPA.
     *
     * @param userId  ID của người dùng
     * @param amount  Số tiền muốn nạp
     * @param request HttpServletRequest để lấy IP
     * @return URL thanh toán VNPAY
     */
    String initiateVnpayDeposit(Long userId, BigDecimal amount, HttpServletRequest request);

    /**
     * Xử lý callback từ VNPAY sau khi thanh toán.
     * Xác thực chữ ký, cập nhật trạng thái Payment và WalletTransaction.
     *
     * @param request HttpServletRequest chứa các tham số VNPAY
     * @return true nếu xử lý thành công, false nếu không hợp lệ hoặc lỗi
     */
    boolean handleVnpayCallback(HttpServletRequest request);

    /**
     * Thực hiện rút tiền từ ví người dùng.
     * Đây là chức năng nội bộ, không tích hợp với cổng thanh toán bên ngoài.
     * <p>
     * LƯU Ý QUAN TRỌNG: Tương tự như nạp tiền, việc lưu PaymentEntity mà không gán OrderEntity
     * sẽ gây lỗi SQL/JPA nếu PaymentEntity.orderEntity vẫn là nullable = false.
     *
     * @param userId ID của người dùng
     * @param amount Số tiền muốn rút
     * @return true nếu rút tiền thành công, false nếu số dư không đủ
     */
    boolean withdrawFromWallet(Long userId, BigDecimal amount);
}
