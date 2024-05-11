package com.asyraf.inventory.repository;

import com.asyraf.inventory.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
