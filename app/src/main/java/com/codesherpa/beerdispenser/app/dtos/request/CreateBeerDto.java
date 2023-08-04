package com.codesherpa.beerdispenser.app.dtos.request;

import com.codesherpa.beerdispenser.app.exceptions.ExceptionMessage;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateBeerDto {
    @NotNull(message = ExceptionMessage.BEER_NAME_NULL)
    public String name;

    @DecimalMin(value = "0.0", inclusive = true, message = ExceptionMessage.BEER_PRICE_PER_LITRE_INVALID)
    public Float pricePerLitre;
}
