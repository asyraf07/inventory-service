package com.asyraf.inventory.repository;

import com.asyraf.inventory.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    @Query("SELECT COALESCE(SUM(CASE WHEN inv.type = 'T' THEN inv.qty ELSE 0 END), 0) - COALESCE(SUM(CASE WHEN inv.type = 'W' THEN inv.qty ELSE 0 END), 0)  FROM Inventory inv WHERE inv.item.id = :itemId")
    Long getInventoryStockByItemId(Long itemId);
}
