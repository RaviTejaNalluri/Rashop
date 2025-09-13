package com.rashop.product_service.service;
import com.rashop.product_service.domain.Product;
import com.rashop.product_service.domain.ProductStatus;
import java.util.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.rashop.product_service.repository.ProductRepository;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

   @Override
   public Product createProduct(Product product){
        if(product.getSlug()!=null && productRepository.existsBySlug(product.getSlug())){
            throw new IllegalArgumentException("Product slug already exists:"+product.getSlug());
        }
        return productRepository.save(product);
   }

    @Override
    public Product updateProduct(UUID id,Product updates){
        Product existing=getProductById(id);
        if(updates.getSlug()!=null && !updates.getSlug().equals(existing.getSlug()) && productRepository.existsBySlug(updates.getSlug())){
            if(productRepository.findBySlug(updates.getSlug()).isPresent()){
                throw new IllegalArgumentException("Product slug already exists:"+updates.getSlug());
            }
            existing.setSlug(updates.getSlug());
        }
        if (updates.getName() != null) existing.setName(updates.getName());
        if (updates.getDescription() != null) existing.setDescription(updates.getDescription());
        if (updates.getBrand() != null) existing.setBrand(updates.getBrand());
        if (updates.getCategory() != null) existing.setCategory(updates.getCategory());
        if (updates.getStatus() != null) existing.setStatus(updates.getStatus());
        return productRepository.save(existing);

    }

    @Override
    @Transactional(readOnly = true)
    public Product getProductById(UUID id){
        return productRepository.findById(id)
        .orElseThrow(() -> new NoSuchElementException("Product not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Product getProductBySlug(String slug){
        return productRepository.findBySlug(slug)
        .orElseThrow(() -> new NoSuchElementException("Product not found with slug: " + slug));
    }
    @Override
    @Transactional(readOnly = true)
    public Page<Product> list(Pageable pageable){
        return productRepository.findAll(pageable);
    }
    @Override
    @Transactional(readOnly = true)
    public Page<Product> listByStatus(ProductStatus status, Pageable pageable){
        return productRepository.findAllByStatus(status, pageable);
    }
    @Override
    @Transactional(readOnly = true)
    public Page<Product> searchByName(String name, Pageable pageable){
        return productRepository.findByNameContainingIgnoreCase(name, pageable);
    }

    @Override
    public void deleteProduct(UUID id){
        productRepository.deleteById(id);
    }
}
