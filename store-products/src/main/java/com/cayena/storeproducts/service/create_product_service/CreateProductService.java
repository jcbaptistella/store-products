package com.cayena.storeproducts.service.create_product_service;

import com.cayena.storeproducts.dto.product.CreateProductRequestDto;
import com.cayena.storeproducts.entity.Product;
import com.cayena.storeproducts.entity.Supplier;
import com.cayena.storeproducts.exception.GenericException;
import com.cayena.storeproducts.mapper.ProductMapper;
import com.cayena.storeproducts.repository.ProductRepository;
import com.cayena.storeproducts.repository.SupplierRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import static com.cayena.storeproducts.utils.ErrorMessagesUtils.ERROR_MESSAGE_NAME_PRODUCT_NOT_EXISTING;
import static com.cayena.storeproducts.utils.ErrorMessagesUtils.ERROR_MESSAGE_PRODUCT_EXISTING;
import static com.cayena.storeproducts.utils.ErrorMessagesUtils.ERROR_MESSAGE_QUANTITY_NOT_EXISTING;
import static com.cayena.storeproducts.utils.ErrorMessagesUtils.ERROR_MESSAGE_SUPPLIER_ID_NOT_EXISTING;
import static com.cayena.storeproducts.utils.ErrorMessagesUtils.ERROR_MESSAGE_SUPPLIER_NOT_EXISTING;
import static com.cayena.storeproducts.utils.ErrorMessagesUtils.ERROR_MESSAGE_UNIT_PRICE_NOT_EXISTING;
import static java.lang.String.format;

@Service
public class CreateProductService {

    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;
    private final ProductMapper productMapper;

    public CreateProductService(ProductRepository productRepository, SupplierRepository supplierRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.supplierRepository = supplierRepository;
        this.productMapper = productMapper;
    }

    public void createProduct(CreateProductRequestDto createProductRequestDto) {
        validateIncorrectProductFields(createProductRequestDto.getName(),
                createProductRequestDto.getQuantityInStock(),
                createProductRequestDto.getUnitPrice(),
                createProductRequestDto.getSupplierId());

        Product productToCreate = productMapper.dtoToEntity(createProductRequestDto);
        productToCreate.setDateOfCreation(LocalDateTime.now());
        productToCreate.setDateOfLastUpdate(LocalDateTime.now());

        productRepository.save(productToCreate);
    }

    private void validateIncorrectProductFields(String name, Integer quantityInStock, Float unitPrice, Long supplierId) {
        if (Objects.isNull(name)) {
            throw new GenericException(ERROR_MESSAGE_NAME_PRODUCT_NOT_EXISTING);
        }

        Optional<Product> productExisting = productRepository.findByName(name);

        if (productExisting.isPresent()) {
            throw new GenericException(format(ERROR_MESSAGE_PRODUCT_EXISTING, name));
        }

        if (Objects.isNull(quantityInStock)) {
            throw new GenericException(ERROR_MESSAGE_QUANTITY_NOT_EXISTING);
        }

        if (Objects.isNull(unitPrice)) {
            throw new GenericException(ERROR_MESSAGE_UNIT_PRICE_NOT_EXISTING);
        }

        if (Objects.isNull(supplierId)) {
            throw new GenericException(ERROR_MESSAGE_SUPPLIER_ID_NOT_EXISTING);
        }

        Optional<Supplier> supplierExisting = supplierRepository.findById(supplierId);

        if (supplierExisting.isEmpty()) {
            throw new GenericException(format(ERROR_MESSAGE_SUPPLIER_NOT_EXISTING, supplierId));
        }
    }
}
