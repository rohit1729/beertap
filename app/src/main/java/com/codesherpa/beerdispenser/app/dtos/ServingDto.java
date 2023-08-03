package com.codesherpa.beerdispenser.app.dtos;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class ServingDto {
    public Long id;
    public Timestamp startTime;
    public Timestamp endTime;
    public Long beerId;
    public Long tapId;
    public Long promoterId;
    public Long attendeeId;
    public Float flowPerSecond;
    public Float pricePerLitre;
    public Float total;
}
