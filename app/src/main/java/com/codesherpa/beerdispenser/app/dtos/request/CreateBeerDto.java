package com.codesherpa.beerdispenser.app.dtos.request;

import lombok.Data;

@Data
public class CreateBeerDto {
    public String name;
    public Float pricePerLitre;
}
