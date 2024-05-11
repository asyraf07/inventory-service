package com.asyraf.inventory.service;

import com.asyraf.inventory.dto.requests.InventoryRequest;
import com.asyraf.inventory.dto.responses.PageDto;
import com.asyraf.inventory.dto.responses.BaseResponseDto;
import com.asyraf.inventory.dto.responses.InventoryDto;

public interface InventoryService {

    PageDto<InventoryDto> getAllInventory(int page, int size);
    BaseResponseDto<InventoryDto> getInventoryById(Long id);
    BaseResponseDto<InventoryDto> addInventory(InventoryRequest request);
    BaseResponseDto<InventoryDto> updateInventory(Long id, InventoryRequest request);
    BaseResponseDto<InventoryDto> deleteInventory(Long id);

}
