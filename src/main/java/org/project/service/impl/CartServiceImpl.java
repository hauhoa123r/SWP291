// src/main/java/org/project/service/impl/CartServiceImpl.java
package org.project.service.impl;

import org.project.entity.CartItemEntity;
import org.project.repository.CartItemRepository;
import org.project.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartItemRepository cartItemRepository; // Inject CartItemRepository

    @Override
    @Transactional(readOnly = true)
    public BigDecimal calculateCartTotal(Long userId) {
        // Retrieve all cart items for the given user
        List<CartItemEntity> cartItems = cartItemRepository.findByUserEntityId(userId);
        BigDecimal totalAmount = BigDecimal.ZERO;

        // Iterate through each item and calculate its total (price * quantity)
        for (CartItemEntity item : cartItems) {
            // Ensure productEntity is eagerly fetched or accessed within a transaction
            // to avoid LazyInitializationException if not already loaded.
            BigDecimal productPrice = item.getProductEntity().getPrice(); // Get product price
            BigDecimal quantity = BigDecimal.valueOf(item.getQuantity()); // Get item quantity
            BigDecimal itemTotal = productPrice.multiply(quantity); // Calculate total for this item
            totalAmount = totalAmount.add(itemTotal); // Add to overall total
        }
        return totalAmount;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CartItemEntity> getCartItems(Long userId) {
        // Retrieve all cart items for the given user
        return cartItemRepository.findByUserEntityId(userId);
    }
}
