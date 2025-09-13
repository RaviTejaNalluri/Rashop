package com.rashop.product_service.domain;

import jakarta.persistence.*;
import java.util.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name="Prices")

public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name="product_id",nullable=false)
    private Product product;
    
    @Column(nullable=false, length=3) 
    private String currency;

    @Column(nullable=false,precision=19,scale=4)
    private BigDecimal listPrice;
    

    @Column(nullable=false,scale=4)
    private BigDecimal salePrice;
    

    private OffsetDateTime effectiveFrom;

   
    private OffsetDateTime effectiveTo;

    @Column(nullable=false)
    private boolean active=true;

    public UUID getId() {
        return id;
    }
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
    public BigDecimal getListPrice() {
        return listPrice;
    }
    public void setListPrice(BigDecimal listPrice) {
        this.listPrice = listPrice;
    }
    public BigDecimal getSalePrice() {
        return salePrice;
    }
    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }
    public OffsetDateTime getEffectiveFrom() {
        return effectiveFrom;
    }
    public void setEffectiveFrom(OffsetDateTime effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }
    public OffsetDateTime getEffectiveTo() {
        return effectiveTo;
    }
    public void setEffectiveTo(OffsetDateTime effectiveTo) {
        this.effectiveTo = effectiveTo;
    }
    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
}
