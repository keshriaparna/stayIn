package com.project.learning.stayIn.service;

import com.project.learning.stayIn.entity.Room;

public interface InventoryService {

  void initializeRoomForAYear(Room room);

  void deleteFutureInventories(Room room);

}
