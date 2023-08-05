package com.codesherpa.beerdispenser.app.dtos;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TapDto {
    private Long id;
    private String name;
    private Long promoterId;
    private Long beerId;
    private BigDecimal flowPerSecond;
}