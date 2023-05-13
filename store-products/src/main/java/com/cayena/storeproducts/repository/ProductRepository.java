package com.cayena.storeproducts.repository;

import com.cayena.storeproducts.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
