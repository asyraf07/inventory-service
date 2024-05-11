package com.asyraf.inventory.dto.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderRequest {

    @NotBlank(message = "order number must not be blank")
    @NotNull(message = "order number must not be null")
    private String orderNo;

    @NotNull(message = "itemId must not be null")
    private Long itemId;

    @NotNull(message = "quantity must not be null")
    @Min(value = 1, message = "quantity must greater or equal to 1")
    private Long qty;
}
