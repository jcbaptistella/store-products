package com.cayena.storeproducts.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Supplier {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private LocalDateTime dateOfCreation;
    private LocalDateTime dateOfLastUpdate;
    @OneToMany(mappedBy = "supplier")
    private List<Product> products;
}
