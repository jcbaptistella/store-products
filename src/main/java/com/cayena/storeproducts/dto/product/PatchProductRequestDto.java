package com.cayena.storeproducts.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatchProductRequestDto {

    private Long id;
    private String name;
    private Float unitPrice;
    private Long supplierId;
}
