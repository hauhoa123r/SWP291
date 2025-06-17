package org.project.repository;

import org.project.entity.ProductEntity;
import org.project.enums.ProductStatus;
import org.project.enums.ProductType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findAllByProductTypeAndProductStatus(ProductType productType, ProductStatus productStatus);

    Page<ProductEntity> findAllByProductType(ProductType productType, Pageable pageable);

    ProductEntity findByProductTypeAndId(ProductType productType, Long id);

    boolean existsByProductTypeAndId(ProductType productType, Long id);
}
