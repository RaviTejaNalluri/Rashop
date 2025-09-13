package com.rashop.product_service.dto;

import jakarta.validation.constraints.Min;

public class InventoryAdjustRequest {
    @Min(1) private int quantity;

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}