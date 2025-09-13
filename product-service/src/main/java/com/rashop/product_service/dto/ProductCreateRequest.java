package com.rashop.product_service.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.rashop.product_service.domain.ProductStatus;

public class ProductCreateRequest {
    @NotBlank @Size(max = 160) private String slug;
    @NotBlank @Size(max = 160) private String name;
    private String description;
    @Size(max = 120) private String brand;
    @Size(max = 120) private String category;
    private ProductStatus status = ProductStatus.ACTIVE;

    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public ProductStatus getStatus() { return status; }
    public void setStatus(ProductStatus status) { this.status = status; }
}
