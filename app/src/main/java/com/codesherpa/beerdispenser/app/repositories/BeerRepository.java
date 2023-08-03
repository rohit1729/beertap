package com.codesherpa.beerdispenser.app.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.codesherpa.beerdispenser.app.models.Beer;

public interface BeerRepository extends JpaRepository<Beer, Long> {

}