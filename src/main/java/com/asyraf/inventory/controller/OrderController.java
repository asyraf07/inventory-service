package com.asyraf.inventory.controller;

import com.asyraf.inventory.dto.requests.OrderRequest;
import com.asyraf.inventory.dto.responses.BaseResponseDto;
import com.asyraf.inventory.dto.responses.OrderDto;
import com.asyraf.inventory.dto.responses.PageDto;
import com.asyraf.inventory.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@Tag(name = "Order")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<PageDto<OrderDto>> getAll(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "5") int size
    ) {
        return ResponseEntity.ok(orderService.getAllOrder(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponseDto<OrderDto>> getItemById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @PostMapping
    public ResponseEntity<BaseResponseDto<OrderDto>> addItem(@RequestBody OrderRequest request) {
        return ResponseEntity.ok(orderService.addOrder(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponseDto<OrderDto>> updateItem(@PathVariable("id") Long id,
                                                                    @RequestBody OrderRequest request) {
        return ResponseEntity.ok(orderService.updateOrder(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponseDto<OrderDto>> deleteItem(@PathVariable("id") Long id) {
        return ResponseEntity.ok(orderService.deleteOrder(id));
    }

}
