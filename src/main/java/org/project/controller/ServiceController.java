package org.project.controller;

import org.project.model.response.ProductRespsonse;
import org.project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/service")
public class ServiceController {

    private final int PAGE_SIZE = 9;

    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/page/{pageIndex}")
    public String service(@PathVariable int pageIndex, Model model) {
        Page<ProductRespsonse> productRespsonsePage = productService.getAllServicesByPage(pageIndex, PAGE_SIZE);
        if (pageIndex < 0 || pageIndex >= productRespsonsePage.getTotalPages()) {
            return "frontend/404";
        }
        model.addAttribute("services", productRespsonsePage.getContent());
        model.addAttribute("currentPage", pageIndex);
        model.addAttribute("totalPages", productRespsonsePage.getTotalPages());
        return "frontend/service";
    }

    @GetMapping("/detail/{productId}")
    public String serviceDetail(@PathVariable Long productId, Model model) {
        if (!productService.isServiceExist(productId)) {
            return "frontend/404";
        }
        ProductRespsonse productRespsonse = productService.getServiceByProductId(productId);
        model.addAttribute("service", productRespsonse);
        return "frontend/service-detail";
    }
}
