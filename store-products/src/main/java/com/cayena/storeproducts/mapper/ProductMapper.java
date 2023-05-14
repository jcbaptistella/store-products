package com.cayena.storeproducts.mapper;

import com.cayena.storeproducts.dto.product.CreateProductRequestDto;
import com.cayena.storeproducts.dto.product.ProductResponseDto;
import com.cayena.storeproducts.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(uses = { SupplierMapper.class })
public interface ProductMapper {

    @Mapping(source = "supplier", target = "supplierResponseDto")
    ProductResponseDto entityToDto(Product product);
    List<ProductResponseDto> entitiesToDtos(List<Product> productList);
    @Mapping(source = "supplierId", target = "supplier.id")
    Product dtoToEntity(CreateProductRequestDto createProductRequestDto);

}
