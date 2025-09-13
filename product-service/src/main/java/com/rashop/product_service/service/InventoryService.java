package com.rashop.product_service.service;

import com.rashop.product_service.domain.Inventory;
import java.util.*;

public interface InventoryService {
    Inventory getBySku(String sku);
    List<Inventory> getByProductId(UUID productId);

    Inventory increaseAvailable(String sku,int quantity);
    Inventory reserve(String sku, int quantity);
    Inventory release(String sku,int quantity);
    Inventory deductReserved(String sku,int quantity);
}
