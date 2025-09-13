package com.rashop.product_service.repository;

import com.rashop.product_service.domain.Price;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;

public interface PriceRepository extends JpaRepository<Price, UUID> {
    List<Price> findByProductIdAndActiveIsTrueOrderByEffectiveFromDesc(UUID productId);
    List<Price> findByProductIdOrderByEffectiveFromDesc(UUID productId,Pageable pageable);
}


