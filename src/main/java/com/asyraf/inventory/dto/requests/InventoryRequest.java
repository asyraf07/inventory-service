package com.asyraf.inventory.dto.requests;

import com.asyraf.inventory.enums.InventoryType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InventoryRequest {

    @NotNull(message = "itemId must not be null")
    private Long itemId;

    @NotNull(message = "quantity must not be null")
    @Min(value = 1, message = "quantity must greater or equal to 1")
    private Long qty;

    @NotNull(message = "type must not be null")
    private InventoryType type;

}
