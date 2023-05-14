package com.cayena.storeproducts.service.patch_product_quantity_stock_service;

import com.cayena.storeproducts.dto.enums.QuantityStockActionEnum;
import com.cayena.storeproducts.dto.product.PatchQuantityStockRequestDto;
import com.cayena.storeproducts.dto.product.ProductResponseDto;
import com.cayena.storeproducts.entity.Product;
import com.cayena.storeproducts.exception.GenericException;
import com.cayena.storeproducts.exception.NotFoundException;
import com.cayena.storeproducts.mapper.ProductMapper;
import com.cayena.storeproducts.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import static com.cayena.storeproducts.utils.ErrorMessagesUtils.ERROR_MESSAGE_DIFFERENT_ID;
import static com.cayena.storeproducts.utils.ErrorMessagesUtils.ERROR_MESSAGE_ID_NOT_FOUND;
import static com.cayena.storeproducts.utils.ErrorMessagesUtils.ERROR_MESSAGE_QUANTITY_NEGATIVE;
import static com.cayena.storeproducts.utils.ErrorMessagesUtils.ERROR_MESSAGE_QUANTITY_NOT_EXISTING;
import static java.lang.String.format;

@Service
public class PatchProductQuantityStockService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public PatchProductQuantityStockService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public ProductResponseDto patchProductQuantityStock(Long productId, PatchQuantityStockRequestDto patchQuantityStockRequestDto) {
        if (!productId.equals(patchQuantityStockRequestDto.getId())) {
            throw new GenericException(format(ERROR_MESSAGE_DIFFERENT_ID, productId, patchQuantityStockRequestDto.getId()));
        }

        if (Objects.isNull(patchQuantityStockRequestDto.getQuantityInStock())) {
            throw new GenericException(ERROR_MESSAGE_QUANTITY_NOT_EXISTING);
        }

        if (patchQuantityStockRequestDto.getQuantityInStock() < 0) {
            throw new GenericException(ERROR_MESSAGE_QUANTITY_NEGATIVE);
        }

        Optional<Product> productToUpdate = productRepository.findById(productId);

        if (productToUpdate.isEmpty()) {
            throw new NotFoundException(format(ERROR_MESSAGE_ID_NOT_FOUND, productId));
        }

        handleNewQuantityStock(productToUpdate.get(), patchQuantityStockRequestDto);

        productToUpdate.get().setDateOfLastUpdate(LocalDateTime.now());

        return productMapper.entityToDto(productRepository.save(productToUpdate.get()));
    }

    private void handleNewQuantityStock(Product productToUpdate, PatchQuantityStockRequestDto patchQuantityStockRequestDto) {
        int newQuantity = 0;

        if (patchQuantityStockRequestDto.getQuantityStockAction().equals(QuantityStockActionEnum.INCREASE)) {
            newQuantity = increaseQuantity(productToUpdate, patchQuantityStockRequestDto);
        } else {
            newQuantity = decreaseQuantity(productToUpdate, patchQuantityStockRequestDto);

            if (newQuantity < 0) {
                throw new GenericException(ERROR_MESSAGE_QUANTITY_NEGATIVE);
            }
        }

        productToUpdate.setQuantityInStock(newQuantity);
    }

    private int decreaseQuantity(Product productToUpdate, PatchQuantityStockRequestDto patchQuantityStockRequestDto) {
        return productToUpdate.getQuantityInStock() - patchQuantityStockRequestDto.getQuantityInStock();
    }

    private int increaseQuantity(Product productToUpdate, PatchQuantityStockRequestDto patchQuantityStockRequestDto) {
        return productToUpdate.getQuantityInStock() + patchQuantityStockRequestDto.getQuantityInStock();
    }
}
