// src/main/java/org/project/service/CartService.java
package org.project.service;

import org.project.entity.CartItemEntity;

import java.math.BigDecimal;
import java.util.List;

public interface CartService {
    /**
     * Calculates the total amount of all products in the user's shopping cart.
     *
     * @param userId The ID of the user.
     * @return The total amount in the shopping cart.
     */
    BigDecimal calculateCartTotal(Long userId);

    /**
     * Retrieves the list of items in the user's shopping cart.
     *
     * @param userId The ID of the user.
     * @return A list of CartItemEntity.
     */
    List<CartItemEntity> getCartItems(Long userId);
}
