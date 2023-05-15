package com.cayena.storeproducts.service.get_product_service;

import com.cayena.storeproducts.dto.product.ProductResponseDto;
import com.cayena.storeproducts.dto.supplier.SupplierResponseDto;
import com.cayena.storeproducts.entity.Product;
import com.cayena.storeproducts.entity.Supplier;
import com.cayena.storeproducts.exception.NotFoundException;
import com.cayena.storeproducts.mapper.ProductMapper;
import com.cayena.storeproducts.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetProductServiceUTest {
    private GetProductService getProductService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);


    @BeforeEach
    void setUp() {
        getProductService = new GetProductService(productRepository, productMapper);
    }

    @Test
    void get_product() {
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

        var supplierResponseDto = new SupplierResponseDto();
        supplierResponseDto.setId(supplier.getId());
        supplierResponseDto.setName(supplier.getName());
        supplierResponseDto.setDateOfCreation(supplier.getDateOfCreation());
        supplierResponseDto.setDateOfLastUpdate(supplier.getDateOfLastUpdate());

        var productResponseDto = new ProductResponseDto();
        productResponseDto.setId(product.getId());
        productResponseDto.setName(product.getName());
        productResponseDto.setQuantityInStock(product.getQuantityInStock());
        productResponseDto.setUnitPrice(product.getUnitPrice());
        productResponseDto.setSupplierResponseDto(supplierResponseDto);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productMapper.entityToDto(product)).thenReturn(productResponseDto);

        // When
        var responseDto = getProductService.getProductById(productId);

        //Then
        assertEquals(responseDto.getId(), productResponseDto.getId());
        assertEquals(responseDto.getName(), productResponseDto.getName());
        assertEquals(responseDto.getQuantityInStock(), productResponseDto.getQuantityInStock());
        assertEquals(responseDto.getUnitPrice(), productResponseDto.getUnitPrice());
        assertEquals(responseDto.getDateOfCreation(), productResponseDto.getDateOfCreation());
        assertEquals(responseDto.getDateOfLastUpdate(), productResponseDto.getDateOfLastUpdate());
        assertEquals(responseDto.getSupplierResponseDto(), productResponseDto.getSupplierResponseDto());

        verify(productRepository).findById(productId);
        verify(productMapper).entityToDto(product);
        verifyNoMoreInteractions(productRepository);
        verifyNoMoreInteractions(productMapper);
    }

    @Test
    void get_product_with_error_id_not_found() {
        // Given
        Long productId = 1L;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // When
        assertThrows(NotFoundException.class, () -> getProductService.getProductById(productId));

        // Then
        verify(productRepository).findById(productId);
    }
}
