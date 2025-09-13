package com.rashop.product_service.service;

import com.rashop.product_service.domain.Price;
import java.math.BigDecimal;
import java.util.*;
import java.time.OffsetDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface PriceService {
    Price setPrice(UUID productId,String currency,BigDecimal listPrice,BigDecimal salePrice,OffsetDateTime effectiveFrom,OffsetDateTime effectiveTo);
    Optional<Price> getCurrentPrice(UUID productId);
    Page<Price> getPriceHistory(UUID productId,Pageable pageable);
    void deactivatePrice(UUID priceId);

}
