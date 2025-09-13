package com.rashop.product_service.domain;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name="inventories",indexes={
    @Index(name="idx_inventory_product_sku",columnList="sku",unique=true)
})
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name="product_id",nullable=false)
    private Product product;

    @Column(nullable=false,unique=true,length=150)
    private String sku;

    @Column(nullable=false)
    private int quantityAvailable=0;

    @Column(nullable=false)
    private int quantityReserved=0;

    @Column(length=100)
    private String warehouse;

    @Version
    private long version;

    public UUID getId() {
        return id;
    }
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
    public String getSku() {
        return sku;
    }
    public void setSku(String sku) {
        this.sku = sku;
    }
    public int getQuantityAvailable() {
        return quantityAvailable;
    }
    public void setQuantityAvailable(int quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }
    public int getQuantityReserved() {
        return quantityReserved;
    }
    public void setQuantityReserved(int quantityReserved) {
        this.quantityReserved = quantityReserved;
    }
    public String getWarehouse() {
        return warehouse;
    }
    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }
    public long getVersion() {
        return version;
    }
}
