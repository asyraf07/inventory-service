package com.asyraf.inventory.controller;

import com.asyraf.inventory.dto.responses.BaseResponseDto;
import com.asyraf.inventory.dto.responses.PageDto;
import com.asyraf.inventory.dto.responses.StockDto;
import com.asyraf.inventory.service.StockService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stock")
@RequiredArgsConstructor
@Tag(name = "Stock")
public class StockController {

    private final StockService stockService;

    @GetMapping
    public ResponseEntity<PageDto<StockDto>> getAll(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "5") int size
    ) {
        return ResponseEntity.ok(stockService.getAllItemStock(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponseDto<StockDto>> getStockById(@PathVariable("id") Long itemId) {
        return ResponseEntity.ok(stockService.getItemStock(itemId));
    }

}
