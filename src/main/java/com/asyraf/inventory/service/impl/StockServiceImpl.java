package com.asyraf.inventory.service.impl;

import com.asyraf.inventory.dto.mapper.StockMapper;
import com.asyraf.inventory.dto.responses.BaseResponseDto;
import com.asyraf.inventory.dto.responses.PageDto;
import com.asyraf.inventory.dto.responses.StockDto;
import com.asyraf.inventory.enums.ResponseCode;
import com.asyraf.inventory.exception.NotFoundException;
import com.asyraf.inventory.model.Item;
import com.asyraf.inventory.repository.InventoryRepository;
import com.asyraf.inventory.repository.ItemRepository;
import com.asyraf.inventory.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

    private final ItemRepository itemRepository;
    private final InventoryRepository inventoryRepository;
    private final StockMapper stockMapper;

    @Override
    public PageDto<StockDto> getAllItemStock(int page, int size) {
        Page<Item> items = itemRepository.findAll(PageRequest.of(page, size));
        return PageDto.<StockDto>builder()
                .failure(false)
                .message(ResponseCode.SUCCESS.getMessage())
                .data(items.getContent().stream().map(item -> {
                    Long stock = inventoryRepository.getInventoryStockByItemId(item.getId());
                    return stockMapper.toStockResponse(item, stock);
                }).collect(Collectors.toList()))
                .currentPage(page)
                .totalPages(items.getTotalPages())
                .totalElements(items.getTotalElements())
                .build();
    }

    @Override
    public BaseResponseDto<StockDto> getItemStock(Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new NotFoundException("Item not found"));
        Long stock = inventoryRepository.getInventoryStockByItemId(item.getId());
        return BaseResponseDto.<StockDto>builder()
                .failure(false)
                .message(ResponseCode.SUCCESS.getMessage())
                .data(stockMapper.toStockResponse(item, stock))
                .build();
    }
}
