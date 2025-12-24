package com.project.learning.stayIn.service;

import com.project.learning.stayIn.dto.RoomDto;
import com.project.learning.stayIn.entity.Hotel;
import com.project.learning.stayIn.entity.Room;
import com.project.learning.stayIn.exception.ResourceNotFoundException;
import com.project.learning.stayIn.repository.HotelRepository;
import com.project.learning.stayIn.repository.InventoryRepository;
import com.project.learning.stayIn.repository.RoomRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomServiceImpl implements RoomService{

  private final RoomRepository roomRepository;
  private final HotelRepository hotelRepository;
  private final InventoryService inventoryService;
  private final ModelMapper modelMapper;

  @Override
  public RoomDto createNewRoom(Long hotelId, RoomDto roomDto) {
    log.info("Creating a new room in hotel with ID: {}", hotelId);
    Hotel hotel= hotelRepository.findById(hotelId)
        .orElseThrow(()-> new ResourceNotFoundException("Hotel with ID: "+hotelId+" does not exist"));
    Room room = modelMapper.map(roomDto,Room.class);
    room.setHotel(hotel);
    room = roomRepository.save(room);
    //create inventory as soon as room is created & if hotel is active
    if(hotel.getActive()){
      inventoryService.initializeRoomForAYear(room);
    }
    return modelMapper.map(room,RoomDto.class);
  }

  @Override
  public List<RoomDto> getAllRoomsInHotel(Long hotelId) {
    log.info("Getting all rooms in hotel with ID: {}", hotelId);
    Hotel hotel= hotelRepository.findById(hotelId)
        .orElseThrow(()-> new ResourceNotFoundException("Hotel with ID: "+hotelId+" does not exist"));

    return hotel.getRooms()
        .stream()
        .map((element)->modelMapper.map(element,RoomDto.class))
        .collect(Collectors.toList());
  }

  @Override
  public RoomDto getRoomById(Long roomId) {
    log.info("Getting the room with ID: {}", roomId);
    Room room = roomRepository
        .findById(roomId)
        .orElseThrow(() -> new ResourceNotFoundException("Room not found with ID: "+roomId));
    return modelMapper.map(room, RoomDto.class);
  }

  @Override
  @Transactional
  public void deleteRoomById(Long roomId) {
    log.info("Deleting the room with ID: {}", roomId);
    Room room = roomRepository
        .findById(roomId)
        .orElseThrow(() -> new ResourceNotFoundException("Room not found with ID: "+roomId));
    //Delete all future inventory for this room
    inventoryService.deleteFutureInventories(room);
    roomRepository.deleteById(roomId);
  }
}
