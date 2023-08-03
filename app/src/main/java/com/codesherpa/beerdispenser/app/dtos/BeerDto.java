package com.codesherpa.beerdispenser.app.dtos;

import lombok.Data;

@Data
public class BeerDto {
    private Long id; 
    private String name;
    private String pricePerLitre;
}