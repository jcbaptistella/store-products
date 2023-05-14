package com.cayena.storeproducts.service.patch_product_service;

import com.cayena.storeproducts.dto.product.PatchProductRequestDto;
import com.cayena.storeproducts.dto.product.ProductResponseDto;
import com.cayena.storeproducts.entity.Product;
import com.cayena.storeproducts.entity.Supplier;
import com.cayena.storeproducts.exception.GenericException;
import com.cayena.storeproducts.exception.NotFoundException;
import com.cayena.storeproducts.mapper.ProductMapper;
import com.cayena.storeproducts.repository.ProductRepository;
import com.cayena.storeproducts.repository.SupplierRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import static com.cayena.storeproducts.utils.ErrorMessagesUtils.ERROR_MESSAGE_DIFFERENT_ID;
import static com.cayena.storeproducts.utils.ErrorMessagesUtils.ERROR_MESSAGE_ID_NOT_FOUND;
import static com.cayena.storeproducts.utils.ErrorMessagesUtils.ERROR_MESSAGE_NAME_PRODUCT_NOT_EXISTING;
import static com.cayena.storeproducts.utils.ErrorMessagesUtils.ERROR_MESSAGE_PRODUCT_EXISTING;
import static com.cayena.storeproducts.utils.ErrorMessagesUtils.ERROR_MESSAGE_SUPPLIER_ID_NOT_EXISTING;
import static com.cayena.storeproducts.utils.ErrorMessagesUtils.ERROR_MESSAGE_SUPPLIER_NOT_EXISTING;
import static com.cayena.storeproducts.utils.ErrorMessagesUtils.ERROR_MESSAGE_UNIT_PRICE_NOT_EXISTING;
import static java.lang.String.format;

@Service
public class PatchProductService {

    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;
    private final ProductMapper productMapper;

    public PatchProductService(ProductRepository productRepository, SupplierRepository supplierRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.supplierRepository = supplierRepository;
        this.productMapper = productMapper;
    }

    public ProductResponseDto patchProduct(Long productId, PatchProductRequestDto patchProductRequestDto) {
        validateIncorrectProductFields(productId, patchProductRequestDto.getId(), patchProductRequestDto.getName(), patchProductRequestDto.getUnitPrice(), patchProductRequestDto.getSupplierId());

        Optional<Product> productToUpdate = productRepository.findById(productId);

        handleValuesProduct(productToUpdate, patchProductRequestDto);

        return productMapper.entityToDto(productRepository.save(productToUpdate.get()));
    }

    private void handleValuesProduct(Optional<Product> productToUpdate, PatchProductRequestDto patchProductRequestDto) {
        productToUpdate.get().setName(patchProductRequestDto.getName());
        productToUpdate.get().setUnitPrice(patchProductRequestDto.getUnitPrice());
        productToUpdate.get().setDateOfLastUpdate(LocalDateTime.now());
        updateNewSupplier(productToUpdate, patchProductRequestDto);
    }

    private void updateNewSupplier(Optional<Product> productToUpdate, PatchProductRequestDto patchProductRequestDto) {
        Optional<Supplier> newSupplier = supplierRepository.findById(patchProductRequestDto.getSupplierId());
        productToUpdate.get().setSupplier(newSupplier.get());
    }

    private void validateIncorrectProductFields(Long productId, Long productIdRequest, String name, Float unitPrice, Long supplierId) {
        if (!productId.equals(productIdRequest)) {
            throw new GenericException(format(ERROR_MESSAGE_DIFFERENT_ID, productId, productIdRequest));
        }

        if (Objects.isNull(name)) {
            throw new GenericException(ERROR_MESSAGE_NAME_PRODUCT_NOT_EXISTING);
        }

        Optional<Product> productExisting = productRepository.findByName(name);

        if (productExisting.isPresent() && !productExisting.get().getId().equals(productIdRequest)) {
            throw new GenericException(format(ERROR_MESSAGE_PRODUCT_EXISTING, name));
        }

        if (Objects.isNull(supplierId)) {
            throw new GenericException(ERROR_MESSAGE_SUPPLIER_ID_NOT_EXISTING);
        }

        Optional<Supplier> supplierExisting = supplierRepository.findById(supplierId);

        if (supplierExisting.isEmpty()) {
            throw new GenericException(format(ERROR_MESSAGE_SUPPLIER_NOT_EXISTING, supplierId));
        }

        Optional<Product> productToUpdate = productRepository.findById(productId);

        if (productToUpdate.isEmpty()) {
            throw new NotFoundException(format(ERROR_MESSAGE_ID_NOT_FOUND, productId));
        }

        if (Objects.isNull(unitPrice)) {
            throw new GenericException(ERROR_MESSAGE_UNIT_PRICE_NOT_EXISTING);
        }


    }
}
