package com.cayena.storeproducts.repository;

import com.cayena.storeproducts.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
}
