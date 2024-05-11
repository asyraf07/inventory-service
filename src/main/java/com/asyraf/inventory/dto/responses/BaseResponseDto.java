package com.asyraf.inventory.dto.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BaseResponseDto<T> {
    private Boolean failure;
    private String message;
    private T data;
}