package com.codesherpa.beerdispenser.app.dtos.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePricingsDto {
    List<CreatePricingDto> pricings;
}
