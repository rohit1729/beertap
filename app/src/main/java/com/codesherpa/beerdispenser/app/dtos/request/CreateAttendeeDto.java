package com.codesherpa.beerdispenser.app.dtos.request;

import com.codesherpa.beerdispenser.app.exceptions.ExceptionMessage;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAttendeeDto {

    @NotNull(message = ExceptionMessage.ATTENDEE_NAME_NULL)
    @NotBlank(message = ExceptionMessage.ATTENDEE_NAME_BLANK)
    public String name;
}
