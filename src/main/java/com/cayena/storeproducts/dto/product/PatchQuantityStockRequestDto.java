package com.cayena.storeproducts.dto.product;

import com.cayena.storeproducts.dto.enums.QuantityStockActionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatchQuantityStockRequestDto {

    private Long id;
    private Integer quantityInStock;
    private QuantityStockActionEnum quantityStockAction;
}
