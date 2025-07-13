// src/main/java/org/project/controller/CartAPI.java
package org.project.controller;

import org.project.entity.CartItemEntity;
import org.project.model.response.CartItemResponse;
import org.project.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cart")
public class CartAPI {

    @Autowired
    private CartService cartService;

    /**
     * Retrieves the total amount in the user's shopping cart.
     * Lấy tổng số tiền trong giỏ hàng của người dùng.
     *
     * @param userId The ID of the user. ID của người dùng.
     * @return ResponseEntity containing the total amount. ResponseEntity chứa tổng số tiền.
     */
    @GetMapping("/{userId}/total")
    public ResponseEntity<BigDecimal> getCartTotal(@PathVariable Long userId) {
        BigDecimal total = cartService.calculateCartTotal(userId);
        return ResponseEntity.ok(total);
    }

    /**
     * Retrieves a list of items in the user's shopping cart, including product information.
     * Lấy danh sách các mục trong giỏ hàng của người dùng, bao gồm thông tin sản phẩm.
     *
     * @param userId The ID of the user. ID của người dùng.
     * @return ResponseEntity containing a list of simplified CartItem objects. ResponseEntity chứa danh sách đối tượng CartItem đơn giản hóa.
     */
    @GetMapping("/{userId}/items")
    public ResponseEntity<List<CartItemResponse>> getCartItems(@PathVariable Long userId) {
        List<CartItemEntity> cartItems = cartService.getCartItems(userId);
        List<CartItemResponse> response = cartItems.stream()
                .map(item -> new CartItemResponse(
                        item.getProductEntity().getName(),
                        item.getQuantity(),
                        item.getProductEntity().getPrice()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }


}
