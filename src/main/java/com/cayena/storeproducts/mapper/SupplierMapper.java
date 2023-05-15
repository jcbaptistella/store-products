package com.cayena.storeproducts.mapper;

import com.cayena.storeproducts.dto.supplier.SupplierResponseDto;
import com.cayena.storeproducts.entity.Supplier;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface SupplierMapper {

    SupplierResponseDto entityToDto(Supplier supplier);

    List<SupplierResponseDto> entitiesToDtos(List<Supplier> supplierList);
}
