package com.rashop.product_service.web;

import com.rashop.product_service.domain.Product;
import com.rashop.product_service.domain.ProductStatus;
import com.rashop.product_service.dto.*;
import com.rashop.product_service.service.ProductService;
import com.rashop.product_service.mapper.ProductMapper;
import com.rashop.product_service.service.PriceService;
import com.rashop.product_service.service.InventoryService;
import java.util.*;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;
    private final PriceService priceService;
    private final InventoryService inventoryService;
    private final ProductMapper productMapper;

    public ProductController(ProductService productService, PriceService priceService, InventoryService inventoryService, ProductMapper productMapper) {
        this.productService = productService;
        this.priceService = priceService;
        this.inventoryService = inventoryService;
        this.productMapper = productMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse create(@Valid @RequestBody ProductCreateRequest request) {
        Product product = productMapper.toEntity(request);
        product = productService.createProduct(product);
        return productMapper.toResponse(product);
    }
    @PutMapping("/{id}")
    public ProductResponse update(@PathVariable UUID id, @RequestBody ProductUpdateRequest req) {
        Product updates = new Product();
        updates.setSlug(req.getSlug());
        updates.setName(req.getName());
        updates.setDescription(req.getDescription());
        updates.setBrand(req.getBrand());
        updates.setCategory(req.getCategory());
        updates.setStatus(req.getStatus());
        return productMapper.toResponse(productService.updateProduct(id, updates));
    }

    @GetMapping("/{id}")
    public ProductResponse getById(@PathVariable UUID id) {
        return productMapper.toResponse(productService.getProductById(id));
    }

    @GetMapping("/slug/{slug}")
    public ProductResponse getBySlug(@PathVariable String slug) {
        return productMapper.toResponse(productService.getProductBySlug(slug));
    }

    @GetMapping
    public Page<ProductResponse> list(@RequestParam(required = false) ProductStatus status,
                                      @RequestParam(required = false, name = "q") String nameQuery,
                                      Pageable pageable) {
        Page<Product> page;
        if (status != null) {
            page = productService.listByStatus(status, pageable);
        } else if (nameQuery != null && !nameQuery.isBlank()) {
            page = productService.searchByName(nameQuery, pageable);
        } else {
            page = productService.list(pageable);
        }
        return page.map(productMapper::toResponse);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        productService.deleteProduct(id);
    }

    // Prices
    @GetMapping("/{id}/price")
    public PriceResponse getCurrentPrice(@PathVariable UUID id) {
        return priceService.getCurrentPrice(id)
            .map(productMapper::toResponse)
            .orElseThrow(() -> new java.util.NoSuchElementException("No active price for product: " + id));
    }

    @PostMapping("/{id}/prices")
    @ResponseStatus(HttpStatus.CREATED)
    public PriceResponse setPrice(@PathVariable UUID id, @Valid @RequestBody PriceSetRequest req) {
        return productMapper.toResponse(
            priceService.setPrice(id, req.getCurrency(), req.getListPrice(), req.getSalePrice(),
                                  req.getEffectiveFrom(), req.getEffectiveTo())
        );
    }

    @GetMapping("/{id}/prices")
    public Page<PriceResponse> getPriceHistory(@PathVariable UUID id, Pageable pageable) {
        return priceService.getPriceHistory(id, pageable).map(productMapper::toResponse);
    }

    // Inventory
    @GetMapping("/{id}/inventory")
    public List<InventoryResponse> getInventoryForProduct(@PathVariable UUID id) {
        return inventoryService.getByProductId(id).stream().map(productMapper::toResponse).toList();
    }
}
