package com.codesherpa.beerdispenser.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codesherpa.beerdispenser.app.models.Material;
public interface MaterialRepository extends JpaRepository<Material, Long> {
    
}