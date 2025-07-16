package org.project.admin.controller;

import org.project.admin.dto.request.ProductRequest;
import org.project.admin.dto.request.ProductSearchRequest;
import org.project.admin.dto.response.ProductResponse;
import org.project.admin.service.ProductService;
import org.project.admin.util.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductAdminController {
    private final ProductService productService;

    @PostMapping
    public ProductResponse create(@RequestBody @Valid ProductRequest request) {
        return productService.create(request);
    }

    @PutMapping("/{id}")
    public ProductResponse update(@PathVariable Long id, @RequestBody @Valid ProductRequest request) {
        return productService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @GetMapping("/{id}")
    public ProductResponse getById(@PathVariable Long id) {
        return productService.getById(id);
    }

    @GetMapping
    public PageResponse<ProductResponse> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return productService.getAll(PageRequest.of(page, size));
    }

    @PostMapping("/search")
    public PageResponse<ProductResponse> search(
            @RequestBody ProductSearchRequest filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return productService.filter(filter, PageRequest.of(page, size));
    }
}

