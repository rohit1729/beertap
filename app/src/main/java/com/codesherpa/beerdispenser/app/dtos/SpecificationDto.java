package com.codesherpa.beerdispenser.app.dtos;

import lombok.Data;

@Data
public class SpecificationDto {
    private Long id;
    private Long categoryId;
    private String name;
}
