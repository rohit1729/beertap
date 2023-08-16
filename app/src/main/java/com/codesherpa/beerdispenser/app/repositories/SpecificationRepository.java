package com.codesherpa.beerdispenser.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codesherpa.beerdispenser.app.models.Specification;
public interface SpecificationRepository extends JpaRepository<Specification, Long> {
 
    List<Specification> findByCategoryId(Long categoryId);
}