package com.asyraf.inventory.model;


import jakarta.persistence.Entity;
import lombok.*;

import java.math.BigDecimal;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Item extends BaseModel {

    private String name;
    private BigDecimal price;

}
