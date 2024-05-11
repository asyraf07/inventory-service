package com.asyraf.inventory.service;

import com.asyraf.inventory.dto.requests.OrderRequest;
import com.asyraf.inventory.dto.responses.OrderDto;
import com.asyraf.inventory.dto.responses.PageDto;
import com.asyraf.inventory.dto.responses.BaseResponseDto;

public interface OrderService {

    PageDto<OrderDto> getAllOrder(int page, int size);
    BaseResponseDto<OrderDto> getOrderById(Long id);
    BaseResponseDto<OrderDto> addOrder(OrderRequest request);
    BaseResponseDto<OrderDto> updateOrder(Long id, OrderRequest request);
    BaseResponseDto<OrderDto> deleteOrder(Long id);

}
