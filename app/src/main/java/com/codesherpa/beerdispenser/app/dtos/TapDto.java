package com.codesherpa.beerdispenser.app.dtos;

import lombok.Data;

@Data
public class TapDto {
    private Long id;
    private String name;
    private Long promoterId;
    private Long beerId;
}