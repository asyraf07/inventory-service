package com.asyraf.inventory.dto.responses;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ItemDto {

    private Long id;
    private String name;
    private BigDecimal price;

}
