package com.project.learning.stayIn.repository;

import com.project.learning.stayIn.entity.Inventory;
import com.project.learning.stayIn.entity.Room;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory,Long> {

  void deleteByDateAfterAndRoom(LocalDate date, Room room);
}
