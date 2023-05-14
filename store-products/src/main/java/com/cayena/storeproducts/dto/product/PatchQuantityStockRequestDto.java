package com.cayena.storeproducts.dto.product;

import com.cayena.storeproducts.dto.enums.QuantityStockActionEnum;

public class PatchQuantityStockRequestDto {

    private Long id;
    private Integer quantityInStock;
    private QuantityStockActionEnum quantityStockAction;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(Integer quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public QuantityStockActionEnum getQuantityStockAction() {
        return quantityStockAction;
    }

    public void setQuantityStockAction(QuantityStockActionEnum quantityStockAction) {
        this.quantityStockAction = quantityStockAction;
    }
}
