package com.codesherpa.beerdispenser.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codesherpa.beerdispenser.app.models.Pricing;


public interface PricingRepository extends JpaRepository<Pricing, Long> {
 
    List<Pricing> findByMaterialIdAndCategoryId(Long materialId, Long categoryId);
}