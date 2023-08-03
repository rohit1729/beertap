package com.codesherpa.beerdispenser.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.codesherpa.beerdispenser.app.models.Serving;

public interface ServingRepository extends JpaRepository<Serving, Long> {

}