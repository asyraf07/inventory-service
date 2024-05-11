package com.asyraf.inventory.dto.mapper;

import com.asyraf.inventory.dto.responses.InventoryDto;
import com.asyraf.inventory.model.Inventory;
import org.springframework.stereotype.Component;

@Component
public class InventoryMapper {
    public InventoryDto toInventoryResponse(Inventory inventory) {
        return InventoryDto.builder()
                .id(inventory.getId())
                .qty(inventory.getQty())
                .type(inventory.getType())
                .itemId(inventory.getItem().getId())
                .itemName(inventory.getItem().getName())
                .build();
    }

}
