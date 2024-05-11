package com.asyraf.inventory.service.impl;

import com.asyraf.inventory.dto.mapper.ItemMapper;
import com.asyraf.inventory.dto.requests.ItemRequest;
import com.asyraf.inventory.dto.responses.PageDto;
import com.asyraf.inventory.dto.responses.BaseResponseDto;
import com.asyraf.inventory.dto.responses.ItemDto;
import com.asyraf.inventory.enums.ResponseCode;
import com.asyraf.inventory.exception.BadRequestException;
import com.asyraf.inventory.exception.NotFoundException;
import com.asyraf.inventory.model.Item;
import com.asyraf.inventory.repository.ItemRepository;
import com.asyraf.inventory.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    @Override
    public PageDto<ItemDto> getAllItem(int page, int size) {
        Page<Item> items = itemRepository.findAll(PageRequest.of(page, size));
        return PageDto.<ItemDto>builder()
                .failure(false)
                .message(ResponseCode.SUCCESS.getMessage())
                .data(items.getContent().stream().map(itemMapper::toItemResponse).collect(Collectors.toList()))
                .currentPage(page)
                .totalPages(items.getTotalPages())
                .totalElements(items.getTotalElements())
                .build();
    }

    @Override
    @SneakyThrows
    public BaseResponseDto<ItemDto> getItemById(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new NotFoundException("Item not found"));
        return BaseResponseDto.<ItemDto>builder()
                .failure(false)
                .message(ResponseCode.SUCCESS.getMessage())
                .data(itemMapper.toItemResponse(item))
                .build();
    }

    @Override
    public BaseResponseDto<ItemDto> addItem(ItemRequest request) {
        Item item = Item.builder()
                .name(request.getName())
                .price(request.getPrice())
                .build();

        itemRepository.save(item);
        return BaseResponseDto.<ItemDto>builder()
                .failure(false)
                .message(ResponseCode.SUCCESS.getMessage())
                .data(itemMapper.toItemResponse(item))
                .build();
    }

    @Override
    public BaseResponseDto<ItemDto> updateItem(Long id, ItemRequest request) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new NotFoundException("Item not found"));
        item.setName(request.getName());
        item.setPrice(request.getPrice());
        itemRepository.save(item);
        return BaseResponseDto.<ItemDto>builder()
                .failure(false)
                .message(ResponseCode.SUCCESS.getMessage())
                .data(itemMapper.toItemResponse(item))
                .build();
    }

    @Override
    public BaseResponseDto<ItemDto> deleteItem(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new NotFoundException("Item not found"));
        try {
            itemRepository.delete(item);
            return BaseResponseDto.<ItemDto>builder()
                    .failure(false)
                    .message(ResponseCode.SUCCESS.getMessage())
                    .data(itemMapper.toItemResponse(item))
                    .build();
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("Item is referenced on the other table");
        }
    }
}
