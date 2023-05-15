package com.cayena.storeproducts.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequestDto {

    private String name;
    private Integer quantityInStock;
    private Float unitPrice;
    private Long supplierId;
}
