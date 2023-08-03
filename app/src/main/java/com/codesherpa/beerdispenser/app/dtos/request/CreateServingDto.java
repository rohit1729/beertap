package com.codesherpa.beerdispenser.app.dtos.request;

import lombok.Data;

@Data
public class CreateServingDto {
    public Long tapId;
    public Long attendeeId;
}
