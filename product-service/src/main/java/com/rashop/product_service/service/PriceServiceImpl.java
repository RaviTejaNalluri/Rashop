package com.rashop.product_service.service;

import com.rashop.product_service.domain.Price;
import java.math.BigDecimal;
import com.rashop.product_service.domain.Product;
import java.util.*;
import java.time.OffsetDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.rashop.product_service.repository.PriceRepository;
import org.springframework.transaction.annotation.Transactional;
import com.rashop.product_service.repository.ProductRepository;
import org.springframework.data.domain.PageImpl;




@Service
@Transactional
public class PriceServiceImpl implements PriceService {
    private final ProductRepository productRepository;
    private final PriceRepository priceRepository;

    public PriceServiceImpl(ProductRepository productRepository, PriceRepository priceRepository) {
        this.productRepository = productRepository;
        this.priceRepository = priceRepository;
    }

    @Override
    public Price setPrice(UUID productId,
                          String currency,
                          BigDecimal listPrice,
                          BigDecimal salePrice,
                          OffsetDateTime effectiveFrom,
                          OffsetDateTime effectiveTo) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new NoSuchElementException("Product not found: " + productId));

        if (listPrice == null || listPrice.signum() < 0) {
            throw new IllegalArgumentException("listPrice must be >= 0");
        }
        if (salePrice != null && salePrice.signum() < 0) {
            throw new IllegalArgumentException("salePrice must be >= 0");
        }

        // Deactivate existing active prices for product
        List<Price> active = priceRepository.findByProductIdAndActiveIsTrueOrderByEffectiveFromDesc(productId);
        for (Price p : active) {
            p.setActive(false);
        }
        priceRepository.saveAll(active);

        Price price = new Price();
        price.setProduct(product);
        price.setCurrency(currency);
        price.setListPrice(listPrice);
        price.setSalePrice(salePrice);
        price.setEffectiveFrom(effectiveFrom);
        price.setEffectiveTo(effectiveTo);
        price.setActive(true);

        return priceRepository.save(price);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Price> getCurrentPrice(UUID productId) {
        List<Price> active = priceRepository.findByProductIdAndActiveIsTrueOrderByEffectiveFromDesc(productId);
        return active.stream().findFirst();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Price> getPriceHistory(UUID productId, Pageable pageable) {
        List<Price> all = priceRepository.findByProductIdOrderByEffectiveFromDesc(productId,pageable);
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), all.size());
        List<Price> slice = start > end ? List.of() : all.subList(start, end);
        return new PageImpl<>(slice, pageable, all.size());
    }

    @Override
    public void deactivatePrice(UUID priceId) {
        Price price = priceRepository.findById(priceId)
            .orElseThrow(() -> new NoSuchElementException("Price not found: " + priceId));
        price.setActive(false);
        priceRepository.save(price);
    }

}
