package com.cayena.storeproducts.service.patch_product_quantity_stock_service;

import com.cayena.storeproducts.dto.enums.QuantityStockActionEnum;
import com.cayena.storeproducts.dto.product.PatchQuantityStockRequestDto;
import com.cayena.storeproducts.dto.product.ProductResponseDto;
import com.cayena.storeproducts.dto.supplier.SupplierResponseDto;
import com.cayena.storeproducts.entity.Product;
import com.cayena.storeproducts.entity.Supplier;
import com.cayena.storeproducts.exception.GenericException;
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
class PatchProductQuantityStockServiceUTest {

    @Autowired
    private PatchProductQuantityStockService patchProductQuantityStockService;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    @BeforeEach
    void setUp() {
        patchProductQuantityStockService = new PatchProductQuantityStockService(productRepository, productMapper);
    }

    @Test
    void patch_product_quantity_stock_to_increase() {
        // Given
        var productId = 1L;
        var newQuantityStock = 2;

        var supplier = getSupplier();

        var productOld = new Product();
        productOld.setId(1L);
        productOld.setName("Tomate");
        productOld.setQuantityInStock(1);
        productOld.setUnitPrice(1.50F);
        productOld.setSupplier(supplier);
        productOld.setDateOfCreation(LocalDateTime.now());
        productOld.setDateOfLastUpdate(LocalDateTime.now());

        var productUpdated = new Product();
        productUpdated.setId(1L);
        productUpdated.setName("Tomate");
        productUpdated.setQuantityInStock(2);
        productUpdated.setUnitPrice(1.50F);
        productUpdated.setSupplier(supplier);
        productUpdated.setDateOfCreation(LocalDateTime.now());
        productUpdated.setDateOfLastUpdate(LocalDateTime.now());

        var patchQuantityStockRequestDto = new PatchQuantityStockRequestDto();
        patchQuantityStockRequestDto.setId(1L);
        patchQuantityStockRequestDto.setQuantityInStock(1);
        patchQuantityStockRequestDto.setQuantityStockAction(QuantityStockActionEnum.INCREASE);

        var supplierResponseDto = getSupplierResponseDto(supplier);

        var productResponseDto = getProductResponseDto(newQuantityStock, productOld, supplierResponseDto);

        when(productRepository.findById(productId)).thenReturn(Optional.of(productOld));
        when(productRepository.save(productOld)).thenReturn(productUpdated);
        when(productMapper.entityToDto(productUpdated)).thenReturn(productResponseDto);

        // When
        var responseDto = patchProductQuantityStockService.patchProductQuantityStock(productId, patchQuantityStockRequestDto);

        assertEquals(responseDto.getId(), productResponseDto.getId());
        assertEquals(responseDto.getName(), productResponseDto.getName());
        assertEquals(responseDto.getQuantityInStock(), productResponseDto.getQuantityInStock());
        assertEquals(responseDto.getUnitPrice(), productResponseDto.getUnitPrice());
        assertEquals(responseDto.getDateOfCreation(), productResponseDto.getDateOfCreation());
        assertEquals(responseDto.getDateOfLastUpdate(), productResponseDto.getDateOfLastUpdate());
        assertEquals(responseDto.getSupplierResponseDto(), productResponseDto.getSupplierResponseDto());

        verify(productRepository).findById(productId);
        verify(productMapper).entityToDto(productUpdated);
        verifyNoMoreInteractions(productRepository);
        verifyNoMoreInteractions(productMapper);
    }

    @Test
    void patch_product_quantity_stock_to_decrease() {
        // Given
        var productId = 1L;
        var newQuantityStock = 1;

        var supplier = getSupplier();

        var productOld = new Product();
        productOld.setId(1L);
        productOld.setName("Tomate");
        productOld.setQuantityInStock(2);
        productOld.setUnitPrice(1.50F);
        productOld.setSupplier(supplier);
        productOld.setDateOfCreation(LocalDateTime.now());
        productOld.setDateOfLastUpdate(LocalDateTime.now());

        var productUpdated = new Product();
        productUpdated.setId(1L);
        productUpdated.setName("Tomate");
        productUpdated.setQuantityInStock(1);
        productUpdated.setUnitPrice(1.50F);
        productUpdated.setSupplier(supplier);
        productUpdated.setDateOfCreation(LocalDateTime.now());
        productUpdated.setDateOfLastUpdate(LocalDateTime.now());

        var patchQuantityStockRequestDto = new PatchQuantityStockRequestDto();
        patchQuantityStockRequestDto.setId(1L);
        patchQuantityStockRequestDto.setQuantityInStock(1);
        patchQuantityStockRequestDto.setQuantityStockAction(QuantityStockActionEnum.DECREASE);

        var supplierResponseDto = getSupplierResponseDto(supplier);

        var productResponseDto = getProductResponseDto(newQuantityStock, productOld, supplierResponseDto);

        when(productRepository.findById(productId)).thenReturn(Optional.of(productOld));
        when(productRepository.save(productOld)).thenReturn(productUpdated);
        when(productMapper.entityToDto(productUpdated)).thenReturn(productResponseDto);

        // When
        var responseDto = patchProductQuantityStockService.patchProductQuantityStock(productId, patchQuantityStockRequestDto);

        assertEquals(responseDto.getId(), productResponseDto.getId());
        assertEquals(responseDto.getName(), productResponseDto.getName());
        assertEquals(responseDto.getQuantityInStock(), productResponseDto.getQuantityInStock());
        assertEquals(responseDto.getUnitPrice(), productResponseDto.getUnitPrice());
        assertEquals(responseDto.getDateOfCreation(), productResponseDto.getDateOfCreation());
        assertEquals(responseDto.getDateOfLastUpdate(), productResponseDto.getDateOfLastUpdate());
        assertEquals(responseDto.getSupplierResponseDto(), productResponseDto.getSupplierResponseDto());

        verify(productRepository).findById(productId);
        verify(productMapper).entityToDto(productUpdated);
        verifyNoMoreInteractions(productRepository);
        verifyNoMoreInteractions(productMapper);
    }

    @Test
    void patch_product_quantity_stock_with_error_different_id() {
        // Given
        var productId = 1L;

        var patchQuantityStockRequestDto = new PatchQuantityStockRequestDto();
        patchQuantityStockRequestDto.setId(2L);
        patchQuantityStockRequestDto.setQuantityInStock(1);
        patchQuantityStockRequestDto.setQuantityStockAction(QuantityStockActionEnum.DECREASE);

        // When
        assertThrows(GenericException.class, () -> patchProductQuantityStockService.patchProductQuantityStock(productId, patchQuantityStockRequestDto));
    }

    @Test
    void patch_product_quantity_stock_with_error_quantity_stock_not_existing() {
        // Given
        var productId = 1L;

        var patchQuantityStockRequestDto = new PatchQuantityStockRequestDto();
        patchQuantityStockRequestDto.setId(1L);
        patchQuantityStockRequestDto.setQuantityStockAction(QuantityStockActionEnum.DECREASE);

        // When
        assertThrows(GenericException.class, () -> patchProductQuantityStockService.patchProductQuantityStock(productId, patchQuantityStockRequestDto));
    }

    @Test
    void patch_product_quantity_stock_with_error_quantity_stock_negative() {
        // Given
        var productId = 1L;

        var patchQuantityStockRequestDto = new PatchQuantityStockRequestDto();
        patchQuantityStockRequestDto.setId(1L);
        patchQuantityStockRequestDto.setQuantityInStock(-1);
        patchQuantityStockRequestDto.setQuantityStockAction(QuantityStockActionEnum.DECREASE);

        // When
        assertThrows(GenericException.class, () -> patchProductQuantityStockService.patchProductQuantityStock(productId, patchQuantityStockRequestDto));
    }


    @Test
    void patch_product_quantity_stock_with_error_product_not_existing() {
        // Given
        var productId = 1L;

        var patchQuantityStockRequestDto = new PatchQuantityStockRequestDto();
        patchQuantityStockRequestDto.setId(1L);
        patchQuantityStockRequestDto.setQuantityInStock(1);
        patchQuantityStockRequestDto.setQuantityStockAction(QuantityStockActionEnum.DECREASE);

        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // When
        assertThrows(NotFoundException.class, () -> patchProductQuantityStockService.patchProductQuantityStock(productId, patchQuantityStockRequestDto));
    }

    @Test
    void patch_product_quantity_stock_with_error_quantity_negative() {
        // Given
        var productId = 1L;

        var patchQuantityStockRequestDto = new PatchQuantityStockRequestDto();
        patchQuantityStockRequestDto.setId(1L);
        patchQuantityStockRequestDto.setQuantityInStock(10);
        patchQuantityStockRequestDto.setQuantityStockAction(QuantityStockActionEnum.DECREASE);

        var supplier = getSupplier();

        var productOld = new Product();
        productOld.setId(1L);
        productOld.setName("Tomate");
        productOld.setQuantityInStock(1);
        productOld.setUnitPrice(1.50F);
        productOld.setSupplier(supplier);
        productOld.setDateOfCreation(LocalDateTime.now());
        productOld.setDateOfLastUpdate(LocalDateTime.now());

        when(productRepository.findById(1L)).thenReturn(Optional.of(productOld));

        // When
        assertThrows(GenericException.class, () -> patchProductQuantityStockService.patchProductQuantityStock(productId, patchQuantityStockRequestDto));
    }

    private ProductResponseDto getProductResponseDto(int newQuantityStock, Product productOld, SupplierResponseDto supplierResponseDto) {
        var productResponseDto = new ProductResponseDto();
        productResponseDto.setId(productOld.getId());
        productResponseDto.setName(productOld.getName());
        productResponseDto.setQuantityInStock(newQuantityStock);
        productResponseDto.setUnitPrice(productOld.getUnitPrice());
        productResponseDto.setSupplierResponseDto(supplierResponseDto);
        return productResponseDto;
    }

    private SupplierResponseDto getSupplierResponseDto(Supplier supplier) {
        var supplierResponseDto = new SupplierResponseDto();
        supplierResponseDto.setId(supplier.getId());
        supplierResponseDto.setName(supplier.getName());
        supplierResponseDto.setDateOfCreation(supplier.getDateOfCreation());
        supplierResponseDto.setDateOfLastUpdate(supplier.getDateOfLastUpdate());
        return supplierResponseDto;
    }

    private Supplier getSupplier() {
        var supplier = new Supplier();
        supplier.setId(1L);
        supplier.setName("Fornecedor A");
        supplier.setDateOfCreation(LocalDateTime.now());
        supplier.setDateOfLastUpdate(LocalDateTime.now());
        return supplier;
    }
}
