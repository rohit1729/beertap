package com.codesherpa.beerdispenser.app.dtos.request;

import java.sql.Timestamp;

import com.codesherpa.beerdispenser.app.exceptions.ExceptionMessage;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateServingDto {
    @NotNull(message = ExceptionMessage.SERVING_END_TIME_NULL)
    public Timestamp endTime;
}
