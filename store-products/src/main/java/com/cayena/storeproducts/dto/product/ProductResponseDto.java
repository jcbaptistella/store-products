package com.cayena.storeproducts.dto.product;

import com.cayena.storeproducts.dto.supplier.SupplierResponseDto;

import java.time.LocalDateTime;

public class ProductResponseDto {
    private Long id;
    private String name;
    private Integer quantityInStock;
    private Float unitPrice;
    private SupplierResponseDto supplierResponseDto;
    private LocalDateTime dateOfCreation;
    private LocalDateTime dateOfLastUpdate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(Integer quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public Float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public SupplierResponseDto getSupplierResponseDto() {
        return supplierResponseDto;
    }

    public void setSupplierResponseDto(SupplierResponseDto supplierResponseDto) {
        this.supplierResponseDto = supplierResponseDto;
    }

    public LocalDateTime getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(LocalDateTime dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public LocalDateTime getDateOfLastUpdate() {
        return dateOfLastUpdate;
    }

    public void setDateOfLastUpdate(LocalDateTime dateOfLastUpdate) {
        this.dateOfLastUpdate = dateOfLastUpdate;
    }
}
