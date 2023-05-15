package com.cayena.storeproducts.service.delete_product_service;

import com.cayena.storeproducts.entity.Product;
import com.cayena.storeproducts.entity.Supplier;
import com.cayena.storeproducts.exception.GenericException;
import com.cayena.storeproducts.repository.ProductRepository;
import com.cayena.storeproducts.service.create_product_service.CreateProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteProductServiceUTest {

    @Autowired
    private DeleteProductService deleteProductService;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        deleteProductService = new DeleteProductService(productRepository);
    }

    @Test
    void delete_product() {
        // Given
        var productId = 1L;

        var supplier = new Supplier();
        supplier.setId(1L);
        supplier.setName("Fornecedor A");
        supplier.setDateOfCreation(LocalDateTime.now());
        supplier.setDateOfLastUpdate(LocalDateTime.now());

        var product = new Product();
        product.setId(productId);
        product.setName("Tomate");
        product.setQuantityInStock(1);
        product.setUnitPrice(1.50F);
        product.setSupplier(supplier);
        product.setDateOfCreation(LocalDateTime.now());
        product.setDateOfCreation(LocalDateTime.now());

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // When
        assertDoesNotThrow(() -> deleteProductService.deleteProduct(productId));

        // Then
        verify(productRepository).findById(productId);
        verify(productRepository).deleteById(productId);
    }

    @Test
    void delete_product_with_error_id_not_found() {
        // Given
        var productId = 1L;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // When
        assertThrows(GenericException.class, () -> deleteProductService.deleteProduct(productId));

        // Then
        verify(productRepository).findById(productId);
    }
}
