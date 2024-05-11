package com.asyraf.inventory.service.impl;

import com.asyraf.inventory.dto.mapper.InventoryMapper;
import com.asyraf.inventory.dto.requests.InventoryRequest;
import com.asyraf.inventory.dto.responses.PageDto;
import com.asyraf.inventory.dto.responses.BaseResponseDto;
import com.asyraf.inventory.dto.responses.InventoryDto;
import com.asyraf.inventory.enums.InventoryType;
import com.asyraf.inventory.enums.ResponseCode;
import com.asyraf.inventory.exception.BadRequestException;
import com.asyraf.inventory.exception.NotFoundException;
import com.asyraf.inventory.model.Inventory;
import com.asyraf.inventory.model.Item;
import com.asyraf.inventory.repository.InventoryRepository;
import com.asyraf.inventory.repository.ItemRepository;
import com.asyraf.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final ItemRepository itemRepository;
    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;

    @Override
    public PageDto<InventoryDto> getAllInventory(int page, int size) {
        Page<Inventory> inventories = inventoryRepository.findAll(PageRequest.of(page, size));
        return PageDto.<InventoryDto>builder()
                .failure(false)
                .message("Success")
                .data(inventories.getContent().stream().map(inventoryMapper::toInventoryResponse).collect(Collectors.toList()))
                .currentPage(page)
                .totalPages(inventories.getTotalPages())
                .totalElements(inventories.getTotalElements())
                .build();
    }

    @Override
    @SneakyThrows
    public BaseResponseDto<InventoryDto> getInventoryById(Long id) {
        Inventory inventory = inventoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Inventory not found"));
        return BaseResponseDto.<InventoryDto>builder()
                .failure(false)
                .message(ResponseCode.SUCCESS.getMessage())
                .data(inventoryMapper.toInventoryResponse(inventory))
                .build();
    }

    @Override
    public BaseResponseDto<InventoryDto> addInventory(InventoryRequest request) {
        Item item = itemRepository.findById(request.getItemId()).orElseThrow(() -> new NotFoundException("Item not found"));
        if (InventoryType.W.getName().equals(request.getType().getName())) {
            Long stock = inventoryRepository.getInventoryStockByItemId(item.getId());
            if (stock - request.getQty() < 0) throw new BadRequestException("Insufficient stock: " + stock);
        }
        Inventory inventory = Inventory.builder()
                .item(item)
                .qty(request.getQty())
                .type(request.getType())
                .build();

        inventoryRepository.save(inventory);
        return BaseResponseDto.<InventoryDto>builder()
                .failure(false)
                .message(ResponseCode.SUCCESS.getMessage())
                .data(inventoryMapper.toInventoryResponse(inventory))
                .build();
    }

    @Override
    public BaseResponseDto<InventoryDto> updateInventory(Long id, InventoryRequest request) {
        Item item = itemRepository.findById(request.getItemId()).orElseThrow(() -> new NotFoundException("Item not found"));
        Inventory inventory = inventoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Inventory not found"));
        if (InventoryType.W.getName().equals(request.getType().getName())) {
            Long stock = inventoryRepository.getInventoryStockByItemId(item.getId());
            if (stock - request.getQty() < 0) throw new BadRequestException("Insufficient stock: " + stock);
        }
        inventory.setItem(item);
        inventory.setQty(request.getQty());
        inventory.setType(request.getType());
        inventoryRepository.save(inventory);
        return BaseResponseDto.<InventoryDto>builder()
                .failure(false)
                .message(ResponseCode.SUCCESS.getMessage())
                .data(inventoryMapper.toInventoryResponse(inventory))
                .build();
    }

    @Override
    public BaseResponseDto<InventoryDto> deleteInventory(Long id) {
        Inventory inventory = inventoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Inventory not found"));
        inventoryRepository.delete(inventory);
        return BaseResponseDto.<InventoryDto>builder()
                .failure(false)
                .message(ResponseCode.SUCCESS.getMessage())
                .data(inventoryMapper.toInventoryResponse(inventory))
                .build();
    }

}
