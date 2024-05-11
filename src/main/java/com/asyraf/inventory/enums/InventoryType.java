package com.asyraf.inventory.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InventoryType {
    T("Top Up"),
    W("Withdrawal");

    private final String name;
}
