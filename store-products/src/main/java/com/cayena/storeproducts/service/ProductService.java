package com.cayena.storeproducts.service;

import com.cayena.storeproducts.dto.enums.QuantityStockActionEnum;
import com.cayena.storeproducts.dto.product.CreateProductRequestDto;
import com.cayena.storeproducts.dto.product.PatchProductRequestDto;
import com.cayena.storeproducts.dto.product.PatchQuantityStockRequestDto;
import com.cayena.storeproducts.dto.product.ProductResponseDto;
import com.cayena.storeproducts.entity.Product;
import com.cayena.storeproducts.exception.GenericException;
import com.cayena.storeproducts.exception.NotFoundException;
import com.cayena.storeproducts.mapper.ProductMapper;
import com.cayena.storeproducts.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.lang.String.format;

@Service
public class ProductService {
    private static final String ERROR_MESSAGE_ID_NOT_FOUND = "Produto com id = %s não encontrado";
    private static final String ERROR_MESSAGE_DIFFERENT_ID = "O id informado para atualização = %s difere do recebido no objeto para atualizar = %s";
    private static final String ERROR_MESSAGE_QUANTITY_NEGATIVE = "Não é possivel atualizar a quantidade de stock do produto para valores negativo";
    private static final String ERROR_MESSAGE_QUANTITY_NOT_FOUND = "Quantidade de estoque para o produto não informada";
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public List<ProductResponseDto> getAllProducts() {
        return productMapper.entitiesToDtos(productRepository.findAll());
    }

    public ProductResponseDto getProductById(Long productId) {
        Optional<Product> product = productRepository.findById(productId);

        if (product.isEmpty()) {
            throw new NotFoundException(format(ERROR_MESSAGE_ID_NOT_FOUND, productId));
        }

        return productMapper.entityToDto(product.get());
    }

    public void createProduct(CreateProductRequestDto createProductRequestDto) {
        Product productToCreate = productMapper.dtoToEntity(createProductRequestDto);
        productToCreate.setDateOfCreation(LocalDateTime.now());
        productToCreate.setDateOfLastUpdate(LocalDateTime.now());

        productRepository.save(productToCreate);
    }

    public ProductResponseDto patchProduct(Long productId, PatchProductRequestDto patchProductRequestDto) {
        if (!productId.equals(patchProductRequestDto.getId())) {
            throw new GenericException(format(ERROR_MESSAGE_DIFFERENT_ID, productId, patchProductRequestDto.getId()));
        }

        Optional<Product> productToUpdate = productRepository.findById(productId);

        if (productToUpdate.isEmpty()) {
            throw new NotFoundException(format(ERROR_MESSAGE_ID_NOT_FOUND, productId));
        }

        handleValuesProduct(productToUpdate, patchProductRequestDto);

        return productMapper.entityToDto(productRepository.save(productToUpdate.get()));
    }

    public void deleteProduct(Long productId) {
        Optional<Product> product = productRepository.findById(productId);

        if (product.isEmpty()) {
            throw new GenericException(format(ERROR_MESSAGE_ID_NOT_FOUND, productId));
        }

        productRepository.deleteById(productId);
    }

    public ProductResponseDto patchQuantityStock(Long productId, PatchQuantityStockRequestDto patchQuantityStockRequestDto) {
        if (!productId.equals(patchQuantityStockRequestDto.getId())) {
            throw new GenericException(format(ERROR_MESSAGE_DIFFERENT_ID, productId, patchQuantityStockRequestDto.getId()));
        }

        if (Objects.isNull(patchQuantityStockRequestDto.getQuantityInStock())) {
            throw new GenericException(ERROR_MESSAGE_QUANTITY_NOT_FOUND);
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

    private void handleValuesProduct(Optional<Product> productToUpdate, PatchProductRequestDto patchProductRequestDto) {
        productToUpdate.get().setName(patchProductRequestDto.getName());
        productToUpdate.get().setUnitPrice(patchProductRequestDto.getUnitPrice());
        productToUpdate.get().setDateOfLastUpdate(LocalDateTime.now());
    }

    private int decreaseQuantity(Product productToUpdate, PatchQuantityStockRequestDto patchQuantityStockRequestDto) {
        return productToUpdate.getQuantityInStock() - patchQuantityStockRequestDto.getQuantityInStock();
    }

    private int increaseQuantity(Product productToUpdate, PatchQuantityStockRequestDto patchQuantityStockRequestDto) {
        return productToUpdate.getQuantityInStock() + patchQuantityStockRequestDto.getQuantityInStock();
    }
}
