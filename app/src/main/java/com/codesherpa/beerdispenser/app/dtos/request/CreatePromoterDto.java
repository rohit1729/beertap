package com.codesherpa.beerdispenser.app.dtos.request;

import lombok.Data;

@Data
public class CreatePromoterDto {
    public String name;
    private boolean active;
}
