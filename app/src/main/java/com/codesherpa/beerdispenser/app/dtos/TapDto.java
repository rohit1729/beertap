package com.codesherpa.beerdispenser.app.dtos;

import lombok.Data;

@Data
public class TapDto {
    private Long id;
    private String name;
    private BeerDto beer;
    
    // getters and setters
}