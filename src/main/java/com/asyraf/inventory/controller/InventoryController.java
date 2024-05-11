package com.asyraf.inventory.controller;

import com.asyraf.inventory.dto.requests.InventoryRequest;
import com.asyraf.inventory.dto.responses.PageDto;
import com.asyraf.inventory.dto.responses.BaseResponseDto;
import com.asyraf.inventory.dto.responses.InventoryDto;
import com.asyraf.inventory.service.InventoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
@Tag(name = "Inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    public ResponseEntity<PageDto<InventoryDto>> getAll(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "5") int size
    ) {
        return ResponseEntity.ok(inventoryService.getAllInventory(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponseDto<InventoryDto>> getItemById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(inventoryService.getInventoryById(id));
    }

    @PostMapping
    public ResponseEntity<BaseResponseDto<InventoryDto>> addItem(@RequestBody InventoryRequest request) {
        return ResponseEntity.ok(inventoryService.addInventory(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponseDto<InventoryDto>> updateItem(@PathVariable("id") Long id,
                                                                    @RequestBody InventoryRequest request) {
        return ResponseEntity.ok(inventoryService.updateInventory(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponseDto<InventoryDto>> deleteItem(@PathVariable("id") Long id) {
        return ResponseEntity.ok(inventoryService.deleteInventory(id));
    }

}
