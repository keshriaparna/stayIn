package com.project.learning.stayIn.service;

import com.project.learning.stayIn.dto.HotelDto;
import com.project.learning.stayIn.dto.HotelInfoDto;
import com.project.learning.stayIn.dto.RoomDto;
import com.project.learning.stayIn.entity.Hotel;
import com.project.learning.stayIn.entity.Room;
import com.project.learning.stayIn.exception.ResourceNotFoundException;
import com.project.learning.stayIn.repository.HotelRepository;
import com.project.learning.stayIn.repository.RoomRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final InventoryService inventoryService;
    private final ModelMapper modelMapper;
    private final RoomRepository roomRepository;

    @Override
    public HotelDto createNewHotel(HotelDto hotelDto) {
        log.info("Creating a new hotel with name as: {}", hotelDto.getName());
        Hotel hotel= modelMapper.map(hotelDto, Hotel.class);
        // the hotel is just onboarded on the platform not having any inventory to book from yet
        hotel.setActive(false);
        hotel = hotelRepository.save(hotel);
        log.info("Created a new hotel with ID: {}", hotelDto.getId());
        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    public HotelDto getHotelById(Long id) {
        log.info("Fetching hotel with ID: {}", id);
        Hotel hotel= hotelRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Hotel with ID: "+id+" does not exist"));
        return modelMapper.map(hotel, HotelDto.class);
    }

  @Override
  public HotelDto updateHotelById(Long id, HotelDto hotelDto) {
    log.info("Updating hotel with ID: {}", id);
    Hotel hotel= hotelRepository.findById(id)
        .orElseThrow(()-> new ResourceNotFoundException("Hotel with ID: "+id+" does not exist"));
    modelMapper.map(hotelDto,hotel);
    hotel.setId(id);
    hotel = hotelRepository.save(hotel);
    return modelMapper.map(hotel,HotelDto.class);
  }

  @Override
  @Transactional
  public void deleteHotelById(Long id) {
    Hotel hotel= hotelRepository.findById(id)
        .orElseThrow(()-> new ResourceNotFoundException("Hotel with ID: "+id+" does not exist"));

    //delete the all inventories for this hotel
    for(Room room:hotel.getRooms()){
      inventoryService.deleteAllInventories(room);
      roomRepository.deleteById(room.getId());
    }
    hotelRepository.deleteById(id);
  }

  @Override
  @Transactional
  public void activateHotel(Long hotelId) {
    log.info("Updating hotel with ID: {}", hotelId);
    Hotel hotel= hotelRepository
        .findById(hotelId)
        .orElseThrow(()-> new ResourceNotFoundException("Hotel with ID: "+hotelId+" does not exist"));
    hotel.setActive(true);
    //Create inventory for all the rooms for this hotel
    //assuming inventory is created only once
    for(Room room:hotel.getRooms()){
      inventoryService.initializeRoomForAYear(room);
    }
  }

  @Override
  public HotelInfoDto getHotelInfoById(Long hotelId) {
    Hotel hotel= hotelRepository
        .findById(hotelId)
        .orElseThrow(()-> new ResourceNotFoundException("Hotel with ID: "+hotelId+" does not exist"));
    List<RoomDto> rooms = hotel.getRooms()
            .stream()
            .map((element)->modelMapper.map(element,RoomDto.class))
            .toList();
    return new HotelInfoDto(modelMapper.map(hotel,HotelDto.class),rooms);
  }
}
