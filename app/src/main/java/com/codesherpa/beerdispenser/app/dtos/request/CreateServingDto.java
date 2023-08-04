package com.codesherpa.beerdispenser.app.dtos.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateServingDto {

    @NotNull(message = "tapId should not be null")
    @Min(value = 1, message = "tapId should be greater than 1")
    public Long tapId;


    @NotNull(message = "attendeeId should not be null")
    @Min(value = 1, message = "attendeeId should be greater than 1")
    public Long attendeeId;
}
