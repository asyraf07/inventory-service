package com.asyraf.inventory.dto.responses;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class StockDto {

    private Long itemId;
    private String name;
    private BigDecimal price;
    private Long stock;

}
