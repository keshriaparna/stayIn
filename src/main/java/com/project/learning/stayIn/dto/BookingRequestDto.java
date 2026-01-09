package com.project.learning.stayIn.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class BookingRequestDto {
  private Long hotelId;
  private Long roomId;
  private LocalDate checkInDate;
  private LocalDate checkoutDate;
  private Integer roomsCount;

}
