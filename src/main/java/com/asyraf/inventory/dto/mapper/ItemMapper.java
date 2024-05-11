package com.asyraf.inventory.dto.mapper;

import com.asyraf.inventory.dto.responses.ItemDto;
import com.asyraf.inventory.model.Item;
import org.springframework.stereotype.Component;

@Component
public class ItemMapper {
    public ItemDto toItemResponse(Item item) {
        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .price(item.getPrice())
                .build();
    }

}
