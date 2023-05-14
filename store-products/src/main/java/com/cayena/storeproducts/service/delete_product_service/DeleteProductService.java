package com.cayena.storeproducts.service.delete_product_service;

import com.cayena.storeproducts.entity.Product;
import com.cayena.storeproducts.exception.GenericException;
import com.cayena.storeproducts.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.cayena.storeproducts.utils.ErrorMessagesUtils.ERROR_MESSAGE_ID_NOT_FOUND;
import static java.lang.String.format;

@Service
public class DeleteProductService {

    private final ProductRepository productRepository;

    public DeleteProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void deleteProduct(Long productId) {
        Optional<Product> product = productRepository.findById(productId);

        if (product.isEmpty()) {
            throw new GenericException(format(ERROR_MESSAGE_ID_NOT_FOUND, productId));
        }

        productRepository.deleteById(productId);
    }
}
