package com.cayena.storeproducts.mapper;

import com.cayena.storeproducts.dto.product.CreateProductRequestDto;
import com.cayena.storeproducts.dto.product.CreateProductResponseDto;
import com.cayena.storeproducts.dto.product.ProductResponseDto;
import com.cayena.storeproducts.entity.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(uses = { SupplierMapper.class })
public interface ProductMapper {

    ProductResponseDto entityToDto(Product product);
    CreateProductResponseDto entityToCreateProductResponseDto(Product product);
    List<ProductResponseDto> entitiesToDtos(List<Product> productList);
    Product dtoToEntity(CreateProductRequestDto createProductRequestDto);

}
