package com.codesherpa.beerdispenser.app.dtos.request;

import java.math.BigDecimal;

import com.codesherpa.beerdispenser.app.exceptions.ExceptionMessage;
import com.codesherpa.beerdispenser.app.models.Tap;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateTapDto {
    @NotNull(message = ExceptionMessage.TAP_NAME_NULL)
    @NotBlank(message = ExceptionMessage.TAP_NAME_BLANK)
    public String name;

    @DecimalMin(value = "0.0", inclusive = true, message = ExceptionMessage.TAP_FLOW_PER_LITRE_INVALID)
    public BigDecimal flowPerSecond;

    @Min(value = 1, message = ExceptionMessage.PROMOTER_ID_INVALID)
    private Long promoterId;

    @Min(value = 1, message = ExceptionMessage.BEER_ID_INVALID)
    private Long beerId;

    public Tap toTap(){
        Tap tap = new Tap();
        tap.setBeerId(this.beerId);
        tap.setPromoterId(this.promoterId);
        tap.setFlowPerSecond(this.flowPerSecond);
        tap.setName(this.name);
        return tap;
    }
}
