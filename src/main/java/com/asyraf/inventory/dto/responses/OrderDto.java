package com.asyraf.inventory.dto.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderDto {

    private Long id;
    private String orderNo;
    private Long itemId;
    private String itemName;
    private Long qty;

}
