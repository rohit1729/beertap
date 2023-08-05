package com.codesherpa.beerdispenser.app.dtos.request;

import com.codesherpa.beerdispenser.app.exceptions.ExceptionMessage;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePromoterDto {

    @NotNull(message = ExceptionMessage.PROMOTER_NAME_NULL)
    @NotEmpty(message = ExceptionMessage.PROMOTER_NAME_BLANK)
    private String name;
    private boolean active;
}
