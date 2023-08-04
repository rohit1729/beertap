package com.codesherpa.beerdispenser.app.dtos;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class BeerDto {
    private Long id; 
    private String name;
    private BigDecimal pricePerLitre;
}