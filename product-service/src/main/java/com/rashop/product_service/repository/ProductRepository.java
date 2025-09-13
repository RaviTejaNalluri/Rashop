package com.rashop.product_service.repository;

import com.rashop.product_service.domain.Product;
import com.rashop.product_service.domain.ProductStatus;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    Optional<Product> findBySlug(String slug);
    boolean existsBySlug(String slug);
    Page<Product> findAllByStatus(ProductStatus status, Pageable pageable);
    Page<Product> findAll(String name, Pageable pageable);
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
