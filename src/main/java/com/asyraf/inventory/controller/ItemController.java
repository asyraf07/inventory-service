package com.asyraf.inventory.controller;

import com.asyraf.inventory.dto.requests.ItemRequest;
import com.asyraf.inventory.dto.responses.PageDto;
import com.asyraf.inventory.dto.responses.BaseResponseDto;
import com.asyraf.inventory.dto.responses.ItemDto;
import com.asyraf.inventory.service.ItemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
@Tag(name = "Item")
@Validated
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public ResponseEntity<PageDto<ItemDto>> getAll(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "5") int size
    ) {
        return ResponseEntity.ok(itemService.getAllItem(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponseDto<ItemDto>> getItemById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(itemService.getItemById(id));
    }

    @PostMapping
    public ResponseEntity<BaseResponseDto<ItemDto>> addItem(@Valid @RequestBody ItemRequest request) {
        return ResponseEntity.ok(itemService.addItem(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponseDto<ItemDto>> updateItem(@PathVariable("id") Long id,
                                                               @Valid @RequestBody ItemRequest request) {
        return ResponseEntity.ok(itemService.updateItem(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponseDto<ItemDto>> deleteItem(@PathVariable("id") Long id) {
        return ResponseEntity.ok(itemService.deleteItem(id));
    }

}
