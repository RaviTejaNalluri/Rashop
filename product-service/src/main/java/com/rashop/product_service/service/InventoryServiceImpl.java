package com.rashop.product_service.service;

import com.rashop.product_service.domain.Inventory;
import java.util.*;
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.Pageable;
// import com.rashop.product_service.domain.Product;
import com.rashop.product_service.repository.InventoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryServiceImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Inventory getBySku(String sku) {
        return inventoryRepository.findBySku(sku)
        .orElseThrow(() -> new NoSuchElementException("Inventory not found with sku: " + sku));
    }
    @Override
    @Transactional(readOnly = true)
    public List<Inventory> getByProductId(UUID productId) {
        return inventoryRepository.findByProductId(productId);
    }
    @Override
    public Inventory increaseAvailable(String sku,int quantity) {
        Inventory inventory = getBySku(sku);
        inventory.setQuantityAvailable(inventory.getQuantityAvailable() + quantity);
        return inventoryRepository.save(inventory);
    }
    @Override
    public Inventory reserve(String sku,int quantity) {
        if(quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        Inventory inventory = getBySku(sku);
        if(inventory.getQuantityAvailable() < quantity) {
            throw new IllegalArgumentException("Quantity available is less than the quantity to reserve");
        }
        inventory.setQuantityAvailable(inventory.getQuantityAvailable() - quantity);
        inventory.setQuantityReserved(inventory.getQuantityReserved() + quantity);
        return inventoryRepository.save(inventory);
    }
    @Override
    public Inventory release(String sku,int quantity) {
        if(quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        Inventory inventory = getBySku(sku);
        if(inventory.getQuantityReserved() < quantity) {
            throw new IllegalArgumentException("Quantity reserved is less than the quantity to release");
        }
        inventory.setQuantityReserved(inventory.getQuantityReserved() - quantity);
        inventory.setQuantityAvailable(inventory.getQuantityAvailable() + quantity);
        return inventoryRepository.save(inventory);
    }
    @Override
    public Inventory deductReserved(String sku,int quantity) {
        if(quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        Inventory inventory = getBySku(sku);
        inventory.setQuantityReserved(inventory.getQuantityReserved() - quantity);
        return inventoryRepository.save(inventory);
    }
    
    
}
