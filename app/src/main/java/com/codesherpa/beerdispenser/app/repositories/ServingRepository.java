package com.codesherpa.beerdispenser.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.codesherpa.beerdispenser.app.models.Serving;

public interface ServingRepository extends JpaRepository<Serving, Long> {

    List<Serving> findByPromoterId(Long promoterId);
}