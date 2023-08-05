package com.codesherpa.beerdispenser.app.dtos.request;

import com.codesherpa.beerdispenser.app.exceptions.ExceptionMessage;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePromoterDto {
    @NotBlank(message = ExceptionMessage.PROMOTER_NAME_BLANK)
    @NotBlank(message = ExceptionMessage.PROMOTER_NAME_NULL)
    private String name;
}
