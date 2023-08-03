package com.codesherpa.beerdispenser.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codesherpa.beerdispenser.app.models.Admin;
public interface AdminRepository extends JpaRepository<Admin, Long> {
    
}