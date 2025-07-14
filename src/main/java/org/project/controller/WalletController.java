// src/main/java/org/project/controller/WalletController.java
package org.project.controller;

import org.project.entity.WalletEntity;
import org.project.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller này xử lý các API liên quan đến quản lý ví người dùng.
 */
@RestController
@RequestMapping("/wallet") // Giữ nguyên base path cho các API liên quan đến ví
public class WalletController {

    @Autowired
    private WalletService walletService; // Sử dụng interface WalletService

    /**
     * Endpoint để lấy thông tin ví của người dùng.
     * Nếu ví chưa tồn tại, hệ thống sẽ tạo ví mới cho người dùng.
     *
     * @param userId ID của người dùng
     * @return ResponseEntity chứa WalletEntity hoặc thông báo lỗi
     */
    @GetMapping("/{userId}")
    public ResponseEntity<WalletEntity> getWallet(@PathVariable Long userId) {
        try {
            WalletEntity wallet = walletService.getOrCreateWallet(userId);
            return ResponseEntity.ok(wallet);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Hoặc trả về một đối tượng lỗi cụ thể
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
