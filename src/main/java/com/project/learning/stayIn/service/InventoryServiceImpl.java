package com.project.learning.stayIn.service;

import com.project.learning.stayIn.dto.HotelDto;
import com.project.learning.stayIn.dto.HotelSearchRequest;
import com.project.learning.stayIn.entity.Hotel;
import com.project.learning.stayIn.entity.Inventory;
import com.project.learning.stayIn.entity.Room;
import com.project.learning.stayIn.repository.InventoryRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceImpl implements InventoryService{

  private final InventoryRepository inventoryRepository;
  private final ModelMapper modelMapper;

  @Override
  public void initializeRoomForAYear(Room room) {
    LocalDate today = LocalDate.now();
    LocalDate endDate = today.plusYears(1);
    for(;!today.isAfter(endDate);today = today.plusDays(1)){
      Inventory inventory = Inventory.builder()
          .hotel(room.getHotel())
          .room(room)
          .bookedCount(0)
          .city(room.getHotel().getCity())
          .date(today)
          .price(room.getBasePrice())
          .surgeFactor(BigDecimal.ONE)
          .totalCount(room.getTotalCount())
          .closed(false)
          .build();
      inventoryRepository.save(inventory);
    }
  }

  @Override
  public void deleteFutureInventories(Room room) {
    LocalDate today = LocalDate.now();
    inventoryRepository.deleteByDateAfterAndRoom(today,room);
  }

  @Override
  public Page<HotelDto> searchHotels(HotelSearchRequest hotelSearchRequest) {
    Pageable pageable = PageRequest.of(hotelSearchRequest.getPage(), hotelSearchRequest.getSize());
    long dateCount =
        ChronoUnit.DAYS.between(hotelSearchRequest.getStartDate(),hotelSearchRequest.getEndDate())+1;
    Page<Hotel> hotelPage = inventoryRepository.findHotelsWithAvailableInventory(hotelSearchRequest.getCity(),
        hotelSearchRequest.getStartDate(),hotelSearchRequest.getEndDate(),hotelSearchRequest.getRoomsCount(),
        dateCount,pageable);
    return hotelPage.map((element)->modelMapper.map(element,HotelDto.class));
  }
}
