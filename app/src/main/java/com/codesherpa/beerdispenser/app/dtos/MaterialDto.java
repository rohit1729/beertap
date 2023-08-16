package com.codesherpa.beerdispenser.app.dtos;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class MaterialDto {
    private Long id;
    private Long categoryId;
    private String name;
    private BigDecimal price;
    private BigDecimal margin;

    public void setCategoryId(Long categoryId){
        this.categoryId = categoryId;
    }
}
