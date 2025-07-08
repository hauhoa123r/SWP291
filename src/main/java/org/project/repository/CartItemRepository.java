package org.project.repository;

import org.project.entity.CartItemEntity;
import org.project.entity.CartItemEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItemEntity, CartItemEntityId> {
    List<CartItemEntity> findByUserEntityId(Long userId);
}
