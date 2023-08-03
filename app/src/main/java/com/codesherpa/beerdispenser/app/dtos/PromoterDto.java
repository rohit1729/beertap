package com.codesherpa.beerdispenser.app.dtos;

import lombok.Data;

@Data
public class PromoterDto {
    private Long id;
    private String name;
    private boolean active;
}
