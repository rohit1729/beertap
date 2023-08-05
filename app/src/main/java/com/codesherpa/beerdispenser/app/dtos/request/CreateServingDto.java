package com.codesherpa.beerdispenser.app.dtos.request;

import com.codesherpa.beerdispenser.app.exceptions.ExceptionMessage;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateServingDto {

    @NotNull(message = ExceptionMessage.TAP_ID_INVALID)
    @Min(value = 1, message = ExceptionMessage.TAP_ID_INVALID)
    public Long tapId;

    @NotNull(message = ExceptionMessage.TAP_ID_INVALID)
    @Min(value = 1, message = ExceptionMessage.ATTENDEE_ID_INVALID)
    public Long attendeeId;
}
