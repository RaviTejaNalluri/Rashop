package com.rashop.product_service.mapper;
import com.rashop.product_service.domain.Product;
import com.rashop.product_service.domain.Price;
import com.rashop.product_service.domain.Inventory;
import com.rashop.product_service.dto.*;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toEntity(ProductCreateRequest req) {
        Product p = new Product();
        p.setSlug(req.getSlug());
        p.setName(req.getName());
        p.setDescription(req.getDescription());
        p.setBrand(req.getBrand());
        p.setCategory(req.getCategory());
        p.setStatus(req.getStatus());
        return p;
    }

    public ProductResponse toResponse(Product p) {
        ProductResponse r = new ProductResponse();
        r.setId(p.getId());
        r.setSlug(p.getSlug());
        r.setName(p.getName());
        r.setDescription(p.getDescription());
        r.setBrand(p.getBrand());
        r.setCategory(p.getCategory());
        r.setStatus(p.getStatus());
        r.setCreatedAt(p.getCreatedAt());
        r.setUpdatedAt(p.getUpdatedAt());
        return r;
    }

    public PriceResponse toResponse(Price price) {
        PriceResponse r = new PriceResponse();
        r.setId(price.getId());
        r.setCurrency(price.getCurrency());
        r.setListPrice(price.getListPrice());
        r.setSalePrice(price.getSalePrice());
        r.setEffectiveFrom(price.getEffectiveFrom());
        r.setEffectiveTo(price.getEffectiveTo());
        r.setActive(price.isActive());
        return r;
    }

    public InventoryResponse toResponse(Inventory inv) {
        InventoryResponse r = new InventoryResponse();
        r.setId(inv.getId());
        r.setSku(inv.getSku());
        r.setQuantityAvailable(inv.getQuantityAvailable());
        r.setQuantityReserved(inv.getQuantityReserved());
        r.setWarehouse(inv.getWarehouse());
        return r;
    }
    
}
