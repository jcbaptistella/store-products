package com.cayena.storeproducts.entity;

import javax.persistence.Id;
import java.time.LocalDateTime;

public class Supplier {

    @Id
    private Long id;
    private String name;
    private LocalDateTime dateOfCreation;
    private LocalDateTime dateOfLastUpdate;
}
