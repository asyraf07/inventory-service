package com.asyraf.inventory.repository;

import com.asyraf.inventory.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
