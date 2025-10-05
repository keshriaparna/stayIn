package com.project.learning.stayIn.repository;

import com.project.learning.stayIn.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory,Long> {
}
