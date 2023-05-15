package com.cayena.storeproducts.service.get_all_product_service;

import com.cayena.storeproducts.dto.product.ProductResponseDto;
import com.cayena.storeproducts.dto.supplier.SupplierResponseDto;
import com.cayena.storeproducts.entity.Product;
import com.cayena.storeproducts.entity.Supplier;
import com.cayena.storeproducts.mapper.ProductMapper;
import com.cayena.storeproducts.repository.ProductRepository;
import com.cayena.storeproducts.service.delete_product_service.DeleteProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAllProductServiceUTest {

    private GetAllProductService getAllProductService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    @BeforeEach
    void setUp() {
        getAllProductService = new GetAllProductService(productRepository, productMapper);
    }

    @Test
    void get_all_products() {
        // Given
        var supplier = new Supplier();
        supplier.setId(1L);
        supplier.setName("Fornecedor A");
        supplier.setDateOfCreation(LocalDateTime.now());
        supplier.setDateOfLastUpdate(LocalDateTime.now());

        var product = new Product();
        product.setId(1L);
        product.setName("Tomate");
        product.setQuantityInStock(10);
        product.setUnitPrice(1.50F);
        product.setSupplier(supplier);
        product.setDateOfCreation(LocalDateTime.now());
        product.setDateOfLastUpdate(LocalDateTime.now());

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


        when(productRepository.findAll()).thenReturn(List.of(product));
        when(productMapper.entitiesToDtos(List.of(product))).thenReturn(List.of(productResponseDto));

        // When
        var responseDtoList = getAllProductService.getAllProducts();

        //Then
        var responseDto = responseDtoList.get(0);

        assertEquals(responseDto.getId(), productResponseDto.getId());
        assertEquals(responseDto.getName(), productResponseDto.getName());
        assertEquals(responseDto.getQuantityInStock(), productResponseDto.getQuantityInStock());
        assertEquals(responseDto.getUnitPrice(), productResponseDto.getUnitPrice());
        assertEquals(responseDto.getDateOfCreation(), productResponseDto.getDateOfCreation());
        assertEquals(responseDto.getDateOfLastUpdate(), productResponseDto.getDateOfLastUpdate());
        assertEquals(responseDto.getSupplierResponseDto(), productResponseDto.getSupplierResponseDto());

        verify(productRepository).findAll();
        verify(productMapper).entitiesToDtos(List.of(product));
        verifyNoMoreInteractions(productRepository);
        verifyNoMoreInteractions(productMapper);
    }
}
