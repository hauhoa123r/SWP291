package org.project.service;

import org.project.entity.OrderItemEntity;
import org.project.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderItemRepository orderItemRepository;


    public List<OrderItemEntity> getOrderItems(Long orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }
}
