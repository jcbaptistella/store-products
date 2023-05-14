package com.cayena.storeproducts.service.product;

import com.cayena.storeproducts.dto.product.ProductRequestDto;
import com.cayena.storeproducts.dto.product.ProductResponseDto;
import com.cayena.storeproducts.entity.Product;
import com.cayena.storeproducts.exception.GenericException;
import com.cayena.storeproducts.exception.NotFoundException;
import com.cayena.storeproducts.mapper.ProductMapper;
import com.cayena.storeproducts.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Service
public class ProductService {

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
            throw new NotFoundException(format("Produto com id = %s não encontrado", productId));
        }

        return productMapper.entityToDto(product.get());
    }

    public void createProduct(ProductRequestDto productRequestDto) {
        productRepository.save(productMapper.dtoToEntity(productRequestDto));
    }

    public ProductResponseDto patchProduct(Long productId, ProductRequestDto productRequestDto) {
        if (!productId.equals(productRequestDto.getId())) {
            throw new GenericException(format("O id informado para atualização = %s difere do recebido no objeto para atualizar = %s", productId, productRequestDto.getId()));
        }

        Optional<Product> product = productRepository.findById(productId);

        if (product.isPresent()) {
            throw new NotFoundException(format("Produto com o id = %s não encontrado", productId));
        }

        return productMapper.entityToDto(productRepository.save(productMapper.dtoToEntity(productRequestDto)));
    }

    public void deleteProduct(Long productId) {
        Optional<Product> product = productRepository.findById(productId);

        if (product.isEmpty()) {
            throw new GenericException(format("Produto com o id = %s não encontrado", productId));
        }

        productRepository.deleteById(productId);
    }

    public ProductResponseDto patchQuantityStock(Long productId, ProductRequestDto productRequestDto) {
        if (!productId.equals(productRequestDto.getId())) {
            throw new GenericException(format("O id informado para atualização = %s difere do recebido no objeto para atualizar = %s", productId, productRequestDto.getId()));
        }

        Optional<Product> product = productRepository.findById(productId);

        if (product.isPresent()) {
            throw new NotFoundException(format("Produto com o id = %s não encontrado", productId));
        }

        return productMapper.entityToDto(productRepository.save(productMapper.dtoToEntity(productRequestDto)));
    }
}
