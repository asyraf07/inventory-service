package com.asyraf.inventory.dto.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ItemRequest {

    @NotBlank(message = "name must not be blank")
    @NotNull(message = "name must not be null")
    private String name;

    @Min(value = 1, message = "price must greater or equal to 1")
    @NotNull(message = "price must not be null")
    private BigDecimal price;
}
