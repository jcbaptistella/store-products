package com.cayena.storeproducts.service.get_product_service;

import com.cayena.storeproducts.dto.product.ProductResponseDto;
import com.cayena.storeproducts.entity.Product;
import com.cayena.storeproducts.exception.NotFoundException;
import com.cayena.storeproducts.mapper.ProductMapper;
import com.cayena.storeproducts.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.cayena.storeproducts.utils.ErrorMessagesUtils.ERROR_MESSAGE_PRODUCT_ID_NOT_FOUND;
import static java.lang.String.format;

@Service
public class GetProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public GetProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public ProductResponseDto getProductById(Long productId) {
        Optional<Product> product = productRepository.findById(productId);

        if (product.isEmpty()) {
            throw new NotFoundException(format(ERROR_MESSAGE_PRODUCT_ID_NOT_FOUND, productId));
        }

        return productMapper.entityToDto(product.get());
    }


}
