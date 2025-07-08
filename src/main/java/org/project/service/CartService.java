package org.project.service;

import org.project.entity.CartItemEntity;
import org.project.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartItemRepository cartItemRepository;


    public List<CartItemEntity> getCartItemsByUserId(Long userId) {
        return cartItemRepository.findByUserEntityId(userId);
    }


}
