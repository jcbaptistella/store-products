package com.cayena.storeproducts.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Product {

    @Id
    private Long id;
    private String name;
    private String quantityInStock;
    private Float unitPrice;
    private Long supplierId;
    private LocalDateTime dateOfCreation;
    private LocalDateTime dateOfLastUpdate;
}
