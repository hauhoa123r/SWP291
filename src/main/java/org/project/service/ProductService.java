package org.project.service;

import org.project.model.response.ProductRespsonse;
import org.springframework.data.domain.Page;

public interface ProductService {
    Page<ProductRespsonse> getAllServicesByPage(int page, int size);

    boolean isServiceExist(Long productId);

    ProductRespsonse getServiceByProductId(Long productId);
}
