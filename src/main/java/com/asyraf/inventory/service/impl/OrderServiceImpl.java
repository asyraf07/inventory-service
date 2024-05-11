package com.asyraf.inventory.service.impl;

import com.asyraf.inventory.dto.mapper.OrderMapper;
import com.asyraf.inventory.dto.requests.OrderRequest;
import com.asyraf.inventory.dto.responses.BaseResponseDto;
import com.asyraf.inventory.dto.responses.OrderDto;
import com.asyraf.inventory.dto.responses.PageDto;
import com.asyraf.inventory.enums.InventoryType;
import com.asyraf.inventory.enums.ResponseCode;
import com.asyraf.inventory.exception.BadRequestException;
import com.asyraf.inventory.exception.NotFoundException;
import com.asyraf.inventory.model.Inventory;
import com.asyraf.inventory.model.Item;
import com.asyraf.inventory.model.Order;
import com.asyraf.inventory.repository.InventoryRepository;
import com.asyraf.inventory.repository.ItemRepository;
import com.asyraf.inventory.repository.OrderRepository;
import com.asyraf.inventory.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final ItemRepository itemRepository;
    private final InventoryRepository inventoryRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public PageDto<OrderDto> getAllOrder(int page, int size) {
        Page<Order> orders = orderRepository.findAll(PageRequest.of(page, size));
        return PageDto.<OrderDto>builder()
                .failure(false)
                .message("Success")
                .data(orders.getContent().stream().map(orderMapper::toOrderResponse).collect(Collectors.toList()))
                .currentPage(page)
                .totalPages(orders.getTotalPages())
                .totalElements(orders.getTotalElements())
                .build();
    }

    @Override
    @SneakyThrows
    public BaseResponseDto<OrderDto> getOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order not found"));
        return BaseResponseDto.<OrderDto>builder()
                .failure(false)
                .message(ResponseCode.SUCCESS.getMessage())
                .data(orderMapper.toOrderResponse(order))
                .build();
    }

    @Override
    @Transactional
    public BaseResponseDto<OrderDto> addOrder(OrderRequest request) {
        Item item = itemRepository.findById(request.getItemId()).orElseThrow(() -> new NotFoundException("Item not found"));
        Long stock = inventoryRepository.getInventoryStockByItemId(request.getItemId());
        if (stock - request.getQty() < 0) throw new BadRequestException("Insufficient stock: " + stock);

        inventoryRepository.save(Inventory.builder()
                .item(item)
                .qty(request.getQty())
                .type(InventoryType.W)
                .build());

        try {
            Order order = orderRepository.save(Order.builder()
                    .orderNo(request.getOrderNo())
                    .item(item)
                    .qty(request.getQty())
                    .build());
            return BaseResponseDto.<OrderDto>builder()
                    .failure(false)
                    .message(ResponseCode.SUCCESS.getMessage())
                    .data(orderMapper.toOrderResponse(order))
                    .build();
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("order number must be unique");
        }
    }

    @Override
    public BaseResponseDto<OrderDto> updateOrder(Long id, OrderRequest request) {
        Item item = itemRepository.findById(request.getItemId()).orElseThrow(() -> new NotFoundException("Item not found"));
        Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order not found"));
        Long stock = inventoryRepository.getInventoryStockByItemId(request.getItemId());
        if (stock + order.getQty() - request.getQty() < 0) throw new BadRequestException("Insufficient stock: " + (stock + order.getQty()));

        order.setOrderNo(request.getOrderNo());
        order.setItem(item);
        order.setQty(request.getQty());
        try {
            orderRepository.save(order);
            return BaseResponseDto.<OrderDto>builder()
                    .failure(false)
                    .message(ResponseCode.SUCCESS.getMessage())
                    .data(orderMapper.toOrderResponse(order))
                    .build();
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("order number must be unique");
        }
    }

    @Override
    public BaseResponseDto<OrderDto> deleteOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order not found"));
        orderRepository.delete(order);
        return BaseResponseDto.<OrderDto>builder()
                .failure(false)
                .message(ResponseCode.SUCCESS.getMessage())
                .data(orderMapper.toOrderResponse(order))
                .build();
    }
}
