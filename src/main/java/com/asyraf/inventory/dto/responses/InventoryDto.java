package com.asyraf.inventory.dto.responses;

import com.asyraf.inventory.enums.InventoryType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InventoryDto {

    private Long id;
    private Long qty;
    private InventoryType type;
    private Long itemId;
    private String itemName;

}
