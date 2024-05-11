package com.asyraf.inventory.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {
    SUCCESS("200", "Success"),
    NOT_FOUND("404","Data not found");

    private final String code;
    private final String message;
}
