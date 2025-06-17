package org.project.service.impl;

import jakarta.transaction.Transactional;
import org.project.converter.ProductConverter;
import org.project.entity.ProductEntity;
import org.project.enums.ProductType;
import org.project.model.response.ProductRespsonse;
import org.project.repository.ProductRepository;
import org.project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    private final ProductType SERVICE_TYPE = ProductType.SERVICE;

    private ProductRepository productRepository;
    private ProductConverter productConverter;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Autowired
    public void setProductConverter(ProductConverter productConverter) {
        this.productConverter = productConverter;
    }

    @Override
    public Page<ProductRespsonse> getAllServicesByPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductEntity> productEntityPage = productRepository.findAllByProductType(SERVICE_TYPE,  pageable);
        return productEntityPage.map(productConverter::toResponse);
    }

    @Override
    public boolean isServiceExist(Long productId) {
        return productRepository.existsByProductTypeAndId(SERVICE_TYPE, productId);
    }

    @Override
    public ProductRespsonse getServiceByProductId(Long productId) {
        return productConverter.toResponse(productRepository.findByProductTypeAndId(SERVICE_TYPE, productId));
    }
}
