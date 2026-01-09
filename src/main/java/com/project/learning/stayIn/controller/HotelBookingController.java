package com.project.learning.stayIn.controller;

import com.project.learning.stayIn.dto.BookingDto;
import com.project.learning.stayIn.dto.BookingRequestDto;
import com.project.learning.stayIn.service.BookingService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookings")
public class HotelBookingController {

  private final BookingService bookingService;

  @PostMapping
  public ResponseEntity<BookingDto> initializeBooking(@RequestBody BookingRequestDto bookingRequestDto){
    return ResponseEntity.ok(bookingService.initializeBooking(bookingRequestDto));
  }
}
