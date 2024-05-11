package com.asyraf.inventory.service;

import com.asyraf.inventory.dto.requests.ItemRequest;
import com.asyraf.inventory.dto.responses.PageDto;
import com.asyraf.inventory.dto.responses.BaseResponseDto;
import com.asyraf.inventory.dto.responses.ItemDto;

public interface ItemService {

    PageDto<ItemDto> getAllItem(int page, int size);
    BaseResponseDto<ItemDto> getItemById(Long id);
    BaseResponseDto<ItemDto> addItem(ItemRequest request);
    BaseResponseDto<ItemDto> updateItem(Long id, ItemRequest request);
    BaseResponseDto<ItemDto> deleteItem(Long id);

}
