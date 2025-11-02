package com.project.learning.stayIn.service;

import com.project.learning.stayIn.dto.HotelDto;
import com.project.learning.stayIn.entity.Hotel;
import com.project.learning.stayIn.exception.ResourceNotFoundException;
import com.project.learning.stayIn.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;

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
  public void deleteHotelById(Long id) {
    boolean exists = hotelRepository.existsById(id);
    if(!exists) throw new ResourceNotFoundException("Hotel with ID: "+id+" does not exist");
    hotelRepository.deleteById(id);
    //TODO: delete the future inventories for this hotel
  }

  @Override
  public void activateHotel(Long hotelId) {
    log.info("Updating hotel with ID: {}", hotelId);
    Hotel hotel= hotelRepository.findById(hotelId)
        .orElseThrow(()-> new ResourceNotFoundException("Hotel with ID: "+hotelId+" does not exist"));
    hotel.setActive(true);
    //TODO: Create inventory for all the rooms for this hotel
  }
}
