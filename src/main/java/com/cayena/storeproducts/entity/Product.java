package com.cayena.storeproducts.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Integer quantityInStock;
    private Float unitPrice;
    @ManyToOne
    private Supplier supplier;
    private LocalDateTime dateOfCreation;
    private LocalDateTime dateOfLastUpdate;
}
