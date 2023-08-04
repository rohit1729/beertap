package com.codesherpa.beerdispenser.app.dtos;

import java.math.BigDecimal;
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
    public BigDecimal flowPerSecond;
    public BigDecimal pricePerLitre;
    public BigDecimal total;
}
