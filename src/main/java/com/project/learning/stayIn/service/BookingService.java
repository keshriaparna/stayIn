package com.project.learning.stayIn.service;

import com.project.learning.stayIn.dto.BookingDto;
import com.project.learning.stayIn.dto.BookingRequestDto;

public interface BookingService {

  public BookingDto initializeBooking(BookingRequestDto bookingRequestDto);

}
