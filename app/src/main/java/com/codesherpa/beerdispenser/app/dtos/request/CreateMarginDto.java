package com.codesherpa.beerdispenser.app.dtos.request;

import java.math.BigDecimal;

import com.codesherpa.beerdispenser.app.exceptions.ExceptionMessage;

import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMarginDto {
    public Long specificationId;
    public Long materialId;
    @DecimalMin(value = "0.0", inclusive = true, message = ExceptionMessage.BEER_PRICE_PER_LITRE_INVALID)
    public BigDecimal margin;
}
