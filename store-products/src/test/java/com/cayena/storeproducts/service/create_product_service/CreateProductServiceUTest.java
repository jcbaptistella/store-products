package com.cayena.storeproducts.service.create_product_service;

import com.cayena.storeproducts.dto.product.CreateProductRequestDto;
import com.cayena.storeproducts.dto.product.CreateProductResponseDto;
import com.cayena.storeproducts.entity.Product;
import com.cayena.storeproducts.entity.Supplier;
import com.cayena.storeproducts.exception.GenericException;
import com.cayena.storeproducts.mapper.ProductMapper;
import com.cayena.storeproducts.repository.ProductRepository;
import com.cayena.storeproducts.repository.SupplierRepository;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateProductServiceUTest {
    @Autowired
    private CreateProductService createProductService;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private SupplierRepository supplierRepository;
    @Mock
    private ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    @BeforeEach
    void setUp() {
        createProductService = new CreateProductService(productRepository, supplierRepository, productMapper);
    }

    @Test
    void create_product_and_return_id() {
        // Given
        var createProductRequestDto = new CreateProductRequestDto();
        createProductRequestDto.setName("Tomate");
        createProductRequestDto.setQuantityInStock(1);
        createProductRequestDto.setUnitPrice(1.50F);
        createProductRequestDto.setSupplierId(1L);

        var supplier = new Supplier();
        supplier.setId(1L);
        supplier.setName("Fornecedor A");
        supplier.setDateOfCreation(LocalDateTime.now());
        supplier.setDateOfLastUpdate(LocalDateTime.now());

        var product = new Product();
        product.setId(1L);
        product.setName(createProductRequestDto.getName());
        product.setQuantityInStock(createProductRequestDto.getQuantityInStock());
        product.setUnitPrice(createProductRequestDto.getUnitPrice());
        product.setSupplier(supplier);
        product.setDateOfCreation(LocalDateTime.now());
        product.setDateOfLastUpdate(LocalDateTime.now());

        var createProductResponseDto = new CreateProductResponseDto();
        createProductResponseDto.setId(1L);

        when(productRepository.findByName(createProductRequestDto.getName())).thenReturn(Optional.empty());
        when(supplierRepository.findById(createProductRequestDto.getSupplierId())).thenReturn(Optional.of(supplier));
        when(productMapper.dtoToEntity(createProductRequestDto)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.entityToCreateProductResponseDto(product)).thenReturn(createProductResponseDto);

        // When
        var responseDto = createProductService.createProduct(createProductRequestDto);

        // Then
        assertEquals(responseDto.getId(), product.getId());

        verify(productRepository).findByName(createProductRequestDto.getName());
        verify(supplierRepository).findById(createProductRequestDto.getSupplierId());
        verify(productMapper).dtoToEntity(createProductRequestDto);
        verify(productRepository).save(product);
        verify(productMapper).entityToCreateProductResponseDto(product);
    }

    @Test
    void create_product_with_error_product_existing() {
        // Given
        var createProductRequestDto = new CreateProductRequestDto();
        createProductRequestDto.setName("Tomate");
        createProductRequestDto.setQuantityInStock(1);
        createProductRequestDto.setUnitPrice(1.50F);
        createProductRequestDto.setSupplierId(1L);

        var supplier = new Supplier();
        supplier.setId(1L);
        supplier.setName("Fornecedor A");
        supplier.setDateOfCreation(LocalDateTime.now());
        supplier.setDateOfLastUpdate(LocalDateTime.now());

        var product = new Product();
        product.setId(1L);
        product.setName(createProductRequestDto.getName());
        product.setQuantityInStock(createProductRequestDto.getQuantityInStock());
        product.setUnitPrice(createProductRequestDto.getUnitPrice());
        product.setSupplier(supplier);

        when(productRepository.findByName(createProductRequestDto.getName())).thenReturn(Optional.of(product));

        // When
        assertThrows(GenericException.class, () -> createProductService.createProduct(createProductRequestDto));

        // Then
        verify(productRepository).findByName(createProductRequestDto.getName());
    }

    @Test
    void create_product_with_error_supplier_not_existing() {
        // Given
        var createProductRequestDto = new CreateProductRequestDto();
        createProductRequestDto.setName("Tomate");
        createProductRequestDto.setQuantityInStock(1);
        createProductRequestDto.setUnitPrice(1.50F);
        createProductRequestDto.setSupplierId(1L);

        when(productRepository.findByName(createProductRequestDto.getName())).thenReturn(Optional.empty());
        when(supplierRepository.findById(createProductRequestDto.getSupplierId())).thenReturn(Optional.empty());

        // When
        assertThrows(GenericException.class, () -> createProductService.createProduct(createProductRequestDto));
    }

    @Test
    void create_product_with_error_name_not_existing() {
        // Given
        var createProductRequestDto = new CreateProductRequestDto();
        createProductRequestDto.setQuantityInStock(1);
        createProductRequestDto.setUnitPrice(1.50F);
        createProductRequestDto.setSupplierId(1L);

        // When
        assertThrows(GenericException.class, () -> createProductService.createProduct(createProductRequestDto));
    }

    @Test
    void create_product_with_error_quantity_stock_not_existing() {
        // Given
        var createProductRequestDto = new CreateProductRequestDto();
        createProductRequestDto.setName("Tomate");
        createProductRequestDto.setUnitPrice(1.50F);
        createProductRequestDto.setSupplierId(1L);

        // When
        assertThrows(GenericException.class, () -> createProductService.createProduct(createProductRequestDto));
    }

    @Test
    void create_product_with_error_unit_price_not_existing() {
        // Given
        var createProductRequestDto = new CreateProductRequestDto();
        createProductRequestDto.setQuantityInStock(1);
        createProductRequestDto.setName("Tomate");
        createProductRequestDto.setSupplierId(1L);

        // When
        assertThrows(GenericException.class, () -> createProductService.createProduct(createProductRequestDto));
    }

    @Test
    void create_product_with_error_supplier_id_not_existing() {
        // Given
        var createProductRequestDto = new CreateProductRequestDto();
        createProductRequestDto.setName("Tomate");
        createProductRequestDto.setQuantityInStock(1);
        createProductRequestDto.setUnitPrice(1.50F);

        // When
        assertThrows(GenericException.class, () -> createProductService.createProduct(createProductRequestDto));
    }
}
