package com.codesherpa.beerdispenser.app.dtos.request;

import lombok.Data;

@Data
public class CreateTapDto {
    public String name;
    public Float flowPerSecond;
}
