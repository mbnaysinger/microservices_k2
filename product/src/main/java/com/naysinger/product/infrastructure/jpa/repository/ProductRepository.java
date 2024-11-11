package com.naysinger.product.infrastructure.jpa.repository;

import com.naysinger.product.domain.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel, Long> {
}