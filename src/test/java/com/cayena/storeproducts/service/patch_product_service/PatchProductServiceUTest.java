package com.cayena.storeproducts.service.patch_product_service;

import com.cayena.storeproducts.dto.product.PatchProductRequestDto;
import com.cayena.storeproducts.dto.product.ProductResponseDto;
import com.cayena.storeproducts.dto.supplier.SupplierResponseDto;
import com.cayena.storeproducts.entity.Product;
import com.cayena.storeproducts.entity.Supplier;
import com.cayena.storeproducts.exception.GenericException;
import com.cayena.storeproducts.exception.NotFoundException;
import com.cayena.storeproducts.mapper.ProductMapper;
import com.cayena.storeproducts.repository.ProductRepository;
import com.cayena.storeproducts.repository.SupplierRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatchProductServiceUTest {
    private PatchProductService patchProductService;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private SupplierRepository supplierRepository;
    @Mock
    private ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    @BeforeEach
    void setUp() {
        patchProductService = new PatchProductService(productRepository, supplierRepository, productMapper);
    }

    @Test
    void patch_product() {
        // Given
        var productId = 1L;

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
        productUpdated.setName("Tomate Seco");
        productUpdated.setQuantityInStock(1);
        productUpdated.setUnitPrice(3.50F);
        productUpdated.setSupplier(supplier);
        productUpdated.setDateOfCreation(LocalDateTime.now());
        productUpdated.setDateOfLastUpdate(LocalDateTime.now());

        var patchProductRequestDto = new PatchProductRequestDto();
        patchProductRequestDto.setId(1L);
        patchProductRequestDto.setName("Tomate Seco");
        patchProductRequestDto.setUnitPrice(3.50F);
        patchProductRequestDto.setSupplierId(1L);

        var supplierResponseDto = getSupplierResponseDto(supplier);

        var productResponseDto = getProductResponseDto(productUpdated, supplierResponseDto);

        when(productRepository.findByName(patchProductRequestDto.getName())).thenReturn(Optional.empty());
        when(supplierRepository.findById(patchProductRequestDto.getSupplierId())).thenReturn(Optional.of(supplier));
        when(productRepository.findById(productId)).thenReturn(Optional.of(productOld));
        when(productRepository.save(productOld)).thenReturn(productUpdated);
        when(productMapper.entityToDto(productUpdated)).thenReturn(productResponseDto);

        // When
        var responseDto = patchProductService.patchProduct(productId, patchProductRequestDto);

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

        var patchProductRequestDto = new PatchProductRequestDto();
        patchProductRequestDto.setId(2L);
        patchProductRequestDto.setName("Tomate");
        patchProductRequestDto.setUnitPrice(1.50F);
        patchProductRequestDto.setSupplierId(1L);

        // When
        assertThrows(GenericException.class, () -> patchProductService.patchProduct(productId, patchProductRequestDto));
    }

    @Test
    void patch_product_with_error_name_product_not_existing() {
        // Given
        var productId = 1L;

        var patchProductRequestDto = new PatchProductRequestDto();
        patchProductRequestDto.setId(1L);
        patchProductRequestDto.setUnitPrice(1.50F);
        patchProductRequestDto.setSupplierId(1L);

        // When
        assertThrows(GenericException.class, () -> patchProductService.patchProduct(productId, patchProductRequestDto));
    }

    @Test
    void patch_product_with_error_product_existing() {
        // Given
        var productId = 1L;

        var supplier = getSupplier();

        var product = new Product();
        product.setId(2L);
        product.setName("Tomate");
        product.setQuantityInStock(1);
        product.setUnitPrice(1.50F);
        product.setSupplier(supplier);
        product.setDateOfCreation(LocalDateTime.now());
        product.setDateOfLastUpdate(LocalDateTime.now());

        var patchProductRequestDto = new PatchProductRequestDto();
        patchProductRequestDto.setId(1L);
        patchProductRequestDto.setName("Tomate");
        patchProductRequestDto.setUnitPrice(1.50F);
        patchProductRequestDto.setSupplierId(1L);

        when(productRepository.findByName(patchProductRequestDto.getName())).thenReturn(Optional.of(product));

        // When
        assertThrows(GenericException.class, () -> patchProductService.patchProduct(productId, patchProductRequestDto));
    }


    @Test
    void patch_product_with_error_supplier_not_existing() {
        // Given
        var productId = 1L;

        var patchProductRequestDto = new PatchProductRequestDto();
        patchProductRequestDto.setId(1L);
        patchProductRequestDto.setName("Tomate");
        patchProductRequestDto.setUnitPrice(1.50F);

        when(productRepository.findByName(patchProductRequestDto.getName())).thenReturn(Optional.empty());

        // When
        assertThrows(GenericException.class, () -> patchProductService.patchProduct(productId, patchProductRequestDto));
    }

    @Test
    void patch_product_with_error_supplier_id_not_existing() {
        // Given
        var productId = 1L;

        var patchProductRequestDto = new PatchProductRequestDto();
        patchProductRequestDto.setId(1L);
        patchProductRequestDto.setName("Tomate");
        patchProductRequestDto.setUnitPrice(1.50F);
        patchProductRequestDto.setSupplierId(1L);

        when(productRepository.findByName(patchProductRequestDto.getName())).thenReturn(Optional.empty());
        when(supplierRepository.findById(patchProductRequestDto.getSupplierId())).thenReturn(Optional.empty());

        // When
        assertThrows(GenericException.class, () -> patchProductService.patchProduct(productId, patchProductRequestDto));
    }

    @Test
    void patch_product_with_error_product_not_existing() {
        // Given
        var productId = 1L;

        var supplier = getSupplier();

        var patchProductRequestDto = new PatchProductRequestDto();
        patchProductRequestDto.setId(1L);
        patchProductRequestDto.setName("Tomate");
        patchProductRequestDto.setUnitPrice(1.50F);
        patchProductRequestDto.setSupplierId(1L);

        when(productRepository.findByName(patchProductRequestDto.getName())).thenReturn(Optional.empty());
        when(supplierRepository.findById(patchProductRequestDto.getSupplierId())).thenReturn(Optional.of(supplier));
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // When
        assertThrows(NotFoundException.class, () -> patchProductService.patchProduct(productId, patchProductRequestDto));
    }

    @Test
    void patch_product_with_error_unit_price_not_existing() {
        // Given
        var productId = 1L;

        var supplier = getSupplier();

        var product = new Product();
        product.setId(1L);
        product.setName("Tomate");
        product.setQuantityInStock(1);
        product.setUnitPrice(1.50F);
        product.setSupplier(supplier);
        product.setDateOfCreation(LocalDateTime.now());
        product.setDateOfLastUpdate(LocalDateTime.now());

        var patchProductRequestDto = new PatchProductRequestDto();
        patchProductRequestDto.setId(1L);
        patchProductRequestDto.setName("Tomate");
        patchProductRequestDto.setSupplierId(1L);

        when(productRepository.findByName(patchProductRequestDto.getName())).thenReturn(Optional.empty());
        when(supplierRepository.findById(patchProductRequestDto.getSupplierId())).thenReturn(Optional.of(supplier));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // When
        assertThrows(GenericException.class, () -> patchProductService.patchProduct(productId, patchProductRequestDto));
    }

    private ProductResponseDto getProductResponseDto(Product productOld, SupplierResponseDto supplierResponseDto) {
        var productResponseDto = new ProductResponseDto();
        productResponseDto.setId(productOld.getId());
        productResponseDto.setName(productOld.getName());
        productResponseDto.setQuantityInStock(productOld.getQuantityInStock());
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
