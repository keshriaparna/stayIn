package com.project.learning.stayIn.service;

import com.project.learning.stayIn.dto.HotelDto;

public interface HotelService {
    HotelDto createNewHotel(HotelDto hotelDto);
    HotelDto getHotelById(Long id);
}
