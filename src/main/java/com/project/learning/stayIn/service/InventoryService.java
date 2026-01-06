package com.project.learning.stayIn.service;

import com.project.learning.stayIn.dto.HotelDto;
import com.project.learning.stayIn.dto.HotelSearchRequest;
import com.project.learning.stayIn.entity.Room;
import org.springframework.data.domain.Page;

public interface InventoryService {

  void initializeRoomForAYear(Room room);

  void deleteAllInventories(Room room);

  Page<HotelDto> searchHotels(HotelSearchRequest hotelSearchRequest);
}
