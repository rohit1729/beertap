package com.codesherpa.beerdispenser.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.codesherpa.beerdispenser.app.models.Tap;

public interface TapRepository extends JpaRepository<Tap, Long> {

}