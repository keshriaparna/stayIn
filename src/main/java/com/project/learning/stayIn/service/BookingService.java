package com.project.learning.stayIn.service;

import com.project.learning.stayIn.dto.BookingDto;
import com.project.learning.stayIn.dto.BookingRequestDto;
import com.project.learning.stayIn.dto.GuestDto;

import java.util.List;

public interface BookingService {

  public BookingDto initializeBooking(BookingRequestDto bookingRequestDto);

  BookingDto addGuests(Long bookingId, List<GuestDto> guestDtoList);
}
