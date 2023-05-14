package com.cayena.storeproducts.controller;

import com.cayena.storeproducts.dto.product.CreateProductRequestDto;
import com.cayena.storeproducts.dto.product.PatchProductRequestDto;
import com.cayena.storeproducts.dto.product.PatchQuantityStockRequestDto;
import com.cayena.storeproducts.dto.product.ProductResponseDto;
import com.cayena.storeproducts.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
class ProductController {

    private final ProductService productService;

    ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public List<ProductResponseDto> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/products/{productId}")
    public ProductResponseDto getProductById(@PathVariable Long productId) {
        return productService.getProductById(productId);
    }

    @PostMapping("/products")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createProduct(@RequestBody CreateProductRequestDto createProductRequestDto) {
        productService.createProduct(createProductRequestDto);
    }

    @PatchMapping("/products/{productId}")
    public ProductResponseDto patchProduct(@PathVariable Long productId, @RequestBody PatchProductRequestDto patchProductRequestDto) {
        return productService.patchProduct(productId, patchProductRequestDto);
    }

    @DeleteMapping("/products/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
    }

    @PatchMapping("/products/{productId}/quantityStock")
    public ProductResponseDto patchQuantityStock(@PathVariable Long productId, @RequestBody PatchQuantityStockRequestDto patchQuantityStockRequestDto) {
        return productService.patchQuantityStock(productId, patchQuantityStockRequestDto);
    }
}
