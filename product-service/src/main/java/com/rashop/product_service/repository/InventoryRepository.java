package com.rashop.product_service.repository;

import com.rashop.product_service.domain.Inventory;
import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, UUID> {
    Optional<Inventory> findBySku(String sku);
    List<Inventory> findByProductId(UUID productId);
}
