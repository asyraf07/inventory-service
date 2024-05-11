package com.asyraf.inventory.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "ORDERS")
public class Order extends BaseModel {

    @ManyToOne
    private Item item;
    @Column(unique = true)
    private String orderNo;
    private Long qty;

}
