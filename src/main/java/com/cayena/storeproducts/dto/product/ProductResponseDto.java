package com.cayena.storeproducts.dto.product;

import com.cayena.storeproducts.dto.supplier.SupplierResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto {
    private Long id;
    private String name;
    private Integer quantityInStock;
    private Float unitPrice;
    private SupplierResponseDto supplierResponseDto;
    private LocalDateTime dateOfCreation;
    private LocalDateTime dateOfLastUpdate;
}
