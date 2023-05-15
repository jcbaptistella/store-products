package com.cayena.storeproducts.controller;

import com.cayena.storeproducts.dto.product.CreateProductRequestDto;
import com.cayena.storeproducts.dto.product.CreateProductResponseDto;
import com.cayena.storeproducts.dto.product.PatchProductRequestDto;
import com.cayena.storeproducts.dto.product.PatchQuantityStockRequestDto;
import com.cayena.storeproducts.dto.product.ProductResponseDto;
import com.cayena.storeproducts.service.create_product_service.CreateProductService;
import com.cayena.storeproducts.service.delete_product_service.DeleteProductService;
import com.cayena.storeproducts.service.get_all_product_service.GetAllProductService;
import com.cayena.storeproducts.service.get_product_service.GetProductService;
import com.cayena.storeproducts.service.patch_product_quantity_stock_service.PatchProductQuantityStockService;
import com.cayena.storeproducts.service.patch_product_service.PatchProductService;
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

    private final CreateProductService createProductService;
    private final DeleteProductService deleteProductService;
    private final  GetAllProductService getAllProductService;
    private final  GetProductService getProductService;
    private final PatchProductQuantityStockService patchProductQuantityStockService;
    private final PatchProductService patchProductService;

    public ProductController(CreateProductService createProductService,
                             DeleteProductService deleteProductService,
                             GetAllProductService getAllProductService,
                             GetProductService getProductService,
                             PatchProductQuantityStockService patchProductQuantityStockService,
                             PatchProductService patchProductService) {
        this.createProductService = createProductService;
        this.deleteProductService = deleteProductService;
        this.getAllProductService = getAllProductService;
        this.getProductService = getProductService;
        this.patchProductQuantityStockService = patchProductQuantityStockService;
        this.patchProductService = patchProductService;
    }

    @GetMapping("/products")
    public List<ProductResponseDto> getAllProducts() {
        return getAllProductService.getAllProducts();
    }

    @GetMapping("/products/{productId}")
    public ProductResponseDto getProductById(@PathVariable Long productId) {
        return getProductService.getProductById(productId);
    }

    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateProductResponseDto createProduct(@RequestBody CreateProductRequestDto createProductRequestDto) {
        return createProductService.createProduct(createProductRequestDto);
    }

    @PatchMapping("/products/{productId}")
    public ProductResponseDto patchProduct(@PathVariable Long productId, @RequestBody PatchProductRequestDto patchProductRequestDto) {
        return patchProductService.patchProduct(productId, patchProductRequestDto);
    }

    @DeleteMapping("/products/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long productId) {
        deleteProductService.deleteProduct(productId);
    }

    @PatchMapping("/products/{productId}/quantityStock")
    public ProductResponseDto patchProductQuantityStock(@PathVariable Long productId, @RequestBody PatchQuantityStockRequestDto patchQuantityStockRequestDto) {
        return patchProductQuantityStockService.patchProductQuantityStock(productId, patchQuantityStockRequestDto);
    }
}
