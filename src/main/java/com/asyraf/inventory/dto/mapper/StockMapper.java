package com.asyraf.inventory.dto.mapper;

import com.asyraf.inventory.dto.responses.StockDto;
import com.asyraf.inventory.model.Item;
import org.springframework.stereotype.Component;

@Component
public class StockMapper {
    public StockDto toStockResponse(Item item, Long stock) {
        return StockDto.builder()
                .itemId(item.getId())
                .name(item.getName())
                .price(item.getPrice())
                .stock(stock)
                .build();
    }

}
