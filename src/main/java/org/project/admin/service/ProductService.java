package org.project.admin.service;

import org.project.admin.dto.request.ProductRequest;
import org.project.admin.dto.request.ProductSearchRequest;
import org.project.admin.dto.response.ProductResponse;
import org.project.admin.util.PageResponse;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    ProductResponse create(ProductRequest request);
    ProductResponse update(Long id, ProductRequest request);
    void delete(Long id);
    ProductResponse getById(Long id);

    PageResponse<ProductResponse> getAll(Pageable pageable);
    PageResponse<ProductResponse> filter(ProductSearchRequest filter, Pageable pageable);
}
