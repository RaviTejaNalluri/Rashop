package com.rashop.product_service.service;

import com.rashop.product_service.domain.Product;
import com.rashop.product_service.domain.ProductStatus;
import java.util.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Product createProduct(Product product);
    Product updateProduct(UUID id,Product product);
    void deleteProduct(UUID id);
    Product getProductById(UUID id);
    Product getProductBySlug(String slug);
    Page<Product> list(Pageable pageable);
    Page<Product> listByStatus(ProductStatus status, Pageable pageable);
    Page<Product> searchByName(String name,Pageable pageable);

}
