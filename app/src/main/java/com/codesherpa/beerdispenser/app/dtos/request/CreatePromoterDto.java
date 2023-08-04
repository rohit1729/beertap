package com.codesherpa.beerdispenser.app.dtos.request;

import com.codesherpa.beerdispenser.app.exceptions.ExceptionMessage;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreatePromoterDto {

    @NotNull(message = ExceptionMessage.PROMOTER_NAME_NULL)
    @NotEmpty(message = ExceptionMessage.PROMOTER_NAME_BLANK)
    public String name;
    private boolean active;
}
