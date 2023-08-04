package com.codesherpa.beerdispenser.app.dtos.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateAdminDto {
    
    @NotEmpty(message = "name should not be empty")
    public String name;
}
