package com.codesherpa.beerdispenser.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.codesherpa.beerdispenser.app.models.Promoter;

public interface PromoterRepository extends JpaRepository<Promoter, Long> {

}