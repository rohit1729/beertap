package com.codesherpa.beerdispenser.app.dtos.request;

import com.codesherpa.beerdispenser.app.exceptions.ExceptionMessage;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class CreateServingDto {

    @Min(value = 1, message = ExceptionMessage.TAP_ID_INVALID)
    public Long tapId;

    @Min(value = 1, message = ExceptionMessage.ATTENDEE_ID_INVALID)
    public Long attendeeId;
}
