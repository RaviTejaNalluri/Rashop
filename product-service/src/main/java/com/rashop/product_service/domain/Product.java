package com.rashop.product_service.domain;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.*;

// We are defining the db here

@Entity
@Table(name = "products",indexes = {
    @Index(name = "idx_product_slug", columnList = "slug",unique = true)
})

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable=false,unique=true,length=150)
    private String slug;

    @Column(nullable=false,length=150)
    private String name;
    
    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length=120)
    private String brand;

    @Column(length=120)
    private String category;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false, length=20)
    private ProductStatus status=ProductStatus.ACTIVE;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Inventory> inventories=new ArrayList<>();

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Price> prices=new ArrayList<>();

    @Column(nullable=false)
    private OffsetDateTime createdAt=OffsetDateTime.now();

    @Column(nullable=false)
    private OffsetDateTime updatedAt=OffsetDateTime.now();

    public UUID getId() {
        return id;
    }
    public String getSlug() {
        return slug;
    }
    public void setSlug(String slug) {
        this.slug = slug;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public ProductStatus getStatus() {
        return status;
    }
    public void setStatus(ProductStatus status) {
        this.status = status;
    }
    public List<Inventory> getInventories() {
        return inventories;
    }
    public List<Price> getPrices() {
        return prices;
    }
    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }
    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }
    @PreUpdate
    public void onUpdate(){
        this.updatedAt=OffsetDateTime.now();
    }
}
