package com.asyraf.inventory.service;

import com.asyraf.inventory.dto.requests.InventoryRequest;
import com.asyraf.inventory.dto.responses.BaseResponseDto;
import com.asyraf.inventory.dto.responses.InventoryDto;
import com.asyraf.inventory.dto.responses.PageDto;
import com.asyraf.inventory.dto.responses.StockDto;

public interface StockService {
    PageDto<StockDto> getAllItemStock(int page, int size);
    BaseResponseDto<StockDto> getItemStock(Long itemId);
}
