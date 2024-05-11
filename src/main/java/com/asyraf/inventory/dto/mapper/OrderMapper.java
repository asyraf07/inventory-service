package com.asyraf.inventory.dto.mapper;

import com.asyraf.inventory.dto.responses.OrderDto;
import com.asyraf.inventory.model.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
    public OrderDto toOrderResponse(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .orderNo(order.getOrderNo())
                .itemId(order.getItem().getId())
                .itemName(order.getItem().getName())
                .qty(order.getQty())
                .build();
    }

}
