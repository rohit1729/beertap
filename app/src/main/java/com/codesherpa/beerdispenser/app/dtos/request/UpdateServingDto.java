package com.codesherpa.beerdispenser.app.dtos.request;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class UpdateServingDto {
    public Timestamp endTime;
}
