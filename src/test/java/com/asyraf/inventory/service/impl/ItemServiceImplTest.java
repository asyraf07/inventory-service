package com.asyraf.inventory.service.impl;

import com.asyraf.inventory.dto.mapper.ItemMapper;
import com.asyraf.inventory.dto.requests.ItemRequest;
import com.asyraf.inventory.dto.responses.BaseResponseDto;
import com.asyraf.inventory.dto.responses.ItemDto;
import com.asyraf.inventory.dto.responses.PageDto;
import com.asyraf.inventory.exception.BadRequestException;
import com.asyraf.inventory.exception.NotFoundException;
import com.asyraf.inventory.model.Item;
import com.asyraf.inventory.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ItemServiceImplTest {

    @InjectMocks
    private ItemServiceImpl itemService;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private ItemMapper itemMapper;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllItem_SuccessTest() {
        int page = 0;
        int size = 5;
        PageRequest pageRequest = PageRequest.of(page, size);
        List<Item> items = new ArrayList<>();
        items.add(Item.builder().name("Laptop").price(new BigDecimal(50L)).build());
        items.add(Item.builder().name("Phone").price(new BigDecimal(10L)).build());

        List<ItemDto> itemDtos = new ArrayList<>();
        itemDtos.add(ItemDto.builder().id(1L).name("Laptop").price(new BigDecimal(50L)).build());
        itemDtos.add(ItemDto.builder().id(2L).name("Phone").price(new BigDecimal(10L)).build());
        Page<Item> itemPage = new PageImpl<>(items);

        when(itemRepository.findAll(pageRequest)).thenReturn(itemPage);
        when(itemMapper.toItemResponse(items.get(0))).thenReturn(itemDtos.get(0));
        when(itemMapper.toItemResponse(items.get(1))).thenReturn(itemDtos.get(1));

        PageDto<ItemDto> actual = itemService.getAllItem(page, size);

        verify(itemRepository).findAll(pageRequest);
        verify(itemMapper, times(2)).toItemResponse(any());
        assertEquals(2, actual.getTotalElements());
    }

    @Test
    void getItem_SuccessTest() {
        Long id = 1L;
        String name = "Laptop";
        BigDecimal price = new BigDecimal(10L);

        Item item = Item.builder().name(name).price(price).build();
        ItemDto itemDto = ItemDto.builder().id(id).name(name).price(price).build();

        when(itemRepository.findById(id)).thenReturn(Optional.of(item));
        when(itemMapper.toItemResponse(item)).thenReturn(itemDto);

        BaseResponseDto<ItemDto> actual = itemService.getItemById(id);

        verify(itemRepository).findById(id);
        verify(itemMapper).toItemResponse(item);
        assertEquals(id, actual.getData().getId());
        assertEquals(name, actual.getData().getName());
        assertEquals(price, actual.getData().getPrice());
    }

    @Test
    void getItem_WhenItemIdNotFound_FailedTest() {
        Long id = 1L;
        when(itemRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> itemService.getItemById(id));
        verify(itemRepository).findById(id);
    }

    @Test
    void addItem_SuccessTest() {
        Long id = 1L;

        ItemRequest request = ItemRequest.builder().name("Laptop").price(new BigDecimal(10L)).build();
        ItemDto itemDto = ItemDto.builder().id(id).name(request.getName()).price(request.getPrice()).build();

        when(itemMapper.toItemResponse(any())).thenReturn(itemDto);

        BaseResponseDto<ItemDto> actual = itemService.addItem(request);

        verify(itemRepository).save(any());
        verify(itemMapper).toItemResponse(any());
        assertEquals(id, actual.getData().getId());
        assertEquals(request.getName(), actual.getData().getName());
        assertEquals(request.getPrice(), actual.getData().getPrice());
    }

    @Test
    void updateItem_SuccessTest() {
        Long id = 1L;
        ItemRequest request = ItemRequest.builder().name("Laptop").price(new BigDecimal(10L)).build();
        ItemDto itemDto = ItemDto.builder().id(id).name(request.getName()).price(request.getPrice()).build();

        when(itemRepository.findById(id)).thenReturn(Optional.of(new Item()));
        when(itemMapper.toItemResponse(any())).thenReturn(itemDto);

        BaseResponseDto<ItemDto> actual = itemService.updateItem(id, request);

        verify(itemRepository).findById(id);
        verify(itemRepository).save(any());
        verify(itemMapper).toItemResponse(any());
        assertEquals(id, actual.getData().getId());
        assertEquals(request.getName(), actual.getData().getName());
        assertEquals(request.getPrice(), actual.getData().getPrice());
    }

    @Test
    void updateItem_WhenItemIdNotFound_FailedTest() {
        Long id = 1L;
        ItemRequest request = ItemRequest.builder().name("Laptop").price(new BigDecimal(10L)).build();
        when(itemRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> itemService.updateItem(id, request));
        verify(itemRepository).findById(id);
    }

    @Test
    void deleteItem_SuccessTest() {
        Long id = 1L;
        Item item = Item.builder().name("Laptop").price(new BigDecimal(50L)).build();
        ItemDto itemDto = ItemDto.builder().id(id).name(item.getName()).price(item.getPrice()).build();

        when(itemRepository.findById(id)).thenReturn(Optional.of(item));
        when(itemMapper.toItemResponse(any())).thenReturn(itemDto);

        BaseResponseDto<ItemDto> actual = itemService.deleteItem(id);

        verify(itemRepository).findById(id);
        verify(itemRepository).delete(item);
        verify(itemMapper).toItemResponse(any());
        assertEquals(id, actual.getData().getId());
        assertEquals(item.getName(), actual.getData().getName());
        assertEquals(item.getPrice(), actual.getData().getPrice());
    }

    @Test
    void deleteItem_WhenItemIdNotFound_FailedTest() {
        Long id = 1L;
        when(itemRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> itemService.deleteItem(id));
        verify(itemRepository).findById(id);
    }

    @Test
    void deleteItem_WhenItemIsReferencedOnOtherTable_FailedTest() {
        Long id = 1L;
        Item item = Item.builder().name("Laptop").price(new BigDecimal(50L)).build();

        when(itemRepository.findById(id)).thenReturn(Optional.of(item));
        doThrow(BadRequestException.class).when(itemRepository).delete(item);

        assertThrows(BadRequestException.class, () -> itemService.deleteItem(id));

        verify(itemRepository).findById(id);
        verify(itemRepository).delete(item);
    }

}