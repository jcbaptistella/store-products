package com.cayena.storeproducts.controller.product;

import com.cayena.storeproducts.dto.product.ProductResponseDto;
import com.cayena.storeproducts.service.product.ProductService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
class ProductController {

    private final ProductService productService;

    ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/api/products")
    public List<ProductResponseDto> getAllProducts() {
        return productService.getAllProducts();
    }
}
