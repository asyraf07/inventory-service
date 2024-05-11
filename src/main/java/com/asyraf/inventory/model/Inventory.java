package com.asyraf.inventory.model;

import com.asyraf.inventory.enums.InventoryType;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Inventory extends BaseModel {

    @ManyToOne
    private Item item;

    private Long qty;

    @Enumerated(EnumType.STRING)
    private InventoryType type;

}
