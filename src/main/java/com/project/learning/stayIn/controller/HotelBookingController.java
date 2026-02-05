package com.project.learning.stayIn.controller;

import com.project.learning.stayIn.dto.BookingDto;
import com.project.learning.stayIn.dto.BookingRequestDto;
import com.project.learning.stayIn.dto.GuestDto;
import com.project.learning.stayIn.service.BookingService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookings")
public class HotelBookingController {

  private final BookingService bookingService;

  @PostMapping("/init")
  public ResponseEntity<BookingDto> initializeBooking(@RequestBody BookingRequestDto bookingRequestDto){
    return ResponseEntity.ok(bookingService.initializeBooking(bookingRequestDto));
  }
  @PostMapping("/{bookingId}/addguests")
  public ResponseEntity<BookingDto> addGuests(@PathVariable Long bookingId, @RequestBody List<GuestDto> guestDtoList){
    return ResponseEntity.ok(bookingService.addGuests(bookingId, guestDtoList));
  }
}
