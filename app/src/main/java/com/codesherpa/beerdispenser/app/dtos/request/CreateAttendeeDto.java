package com.codesherpa.beerdispenser.app.dtos.request;

import com.codesherpa.beerdispenser.app.exceptions.ExceptionMessage;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateAttendeeDto {

    @NotNull(message = ExceptionMessage.ATTENDEE_NAME_NULL)
    public String name;
}
