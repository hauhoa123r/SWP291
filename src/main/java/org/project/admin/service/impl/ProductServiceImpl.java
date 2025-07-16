package org.project.admin.service.impl;

import org.project.admin.dto.request.ProductRequest;
import org.project.admin.dto.request.ProductSearchRequest;
import org.project.admin.dto.response.ProductResponse;
import org.project.admin.entity.Product;
import org.project.admin.mapper.ProductMapper;
import org.project.admin.repository.ProductRepository;
import org.project.admin.service.ProductService;
import org.project.admin.specification.ProductSpecification;
import org.project.admin.util.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service("adminProductServiceImpl")
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductResponse create(ProductRequest request) {
        Product product = productMapper.toEntity(request);
        Product saved = productRepository.save(product);
        return productMapper.toResponse(saved);
    }

    @Override
    public ProductResponse update(Long id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy sản phẩm"));

        // Áp dụng chỉ update các field không null trong request
        productMapper.updateProductFromRequest(request, product);

        Product saved = productRepository.save(product);
        return productMapper.toResponse(saved);
    }

    @Override
    public void delete(Long id) {
        if (!productRepository.existsById(id)) throw new NoSuchElementException("Không tìm thấy sản phẩm");
        productRepository.deleteById(id);
    }

    @Override
    public ProductResponse getById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy sản phẩm"));
        return productMapper.toResponse(product);
    }

    @Override
    public PageResponse<ProductResponse> getAll(Pageable pageable) {
        Page<Product> page = productRepository.findAll(pageable);
        Page<ProductResponse> mapped = page.map(productMapper::toResponse);
        return new PageResponse<>(mapped);
    }

    @Override
    public PageResponse<ProductResponse> filter(ProductSearchRequest filter, Pageable pageable) {
        Page<Product> page = productRepository.findAll(ProductSpecification.filter(filter), pageable);
        Page<ProductResponse> mapped = page.map(productMapper::toResponse);
        return new PageResponse<>(mapped);
    }
}
