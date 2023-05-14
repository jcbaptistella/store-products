package com.cayena.storeproducts.mapper;

import com.cayena.storeproducts.dto.product.ProductRequestDto;
import com.cayena.storeproducts.dto.product.ProductResponseDto;
import com.cayena.storeproducts.entity.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {

    ProductResponseDto entityToDto(Product product);

    List<ProductResponseDto> entitiesToDtos(List<Product> productList);

    Product dtoToEntity(ProductRequestDto productRequestDto);
}
