package com.cayena.storeproducts.service.get_all_product_service;

import com.cayena.storeproducts.dto.product.ProductResponseDto;
import com.cayena.storeproducts.mapper.ProductMapper;
import com.cayena.storeproducts.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public GetAllProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public List<ProductResponseDto> getAllProducts() {
        return productMapper.entitiesToDtos(productRepository.findAll());
    }
}
