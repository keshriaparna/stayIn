package com.project.learning.stayIn.dto;

import com.project.learning.stayIn.entity.Guest;
import com.project.learning.stayIn.entity.Hotel;
import com.project.learning.stayIn.entity.Payment;
import com.project.learning.stayIn.entity.Room;
import com.project.learning.stayIn.entity.User;
import com.project.learning.stayIn.entity.enums.BookingStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

public class BookingDto {

  private Long id;
  private Hotel hotel;
  private Room room;
  private User user;
  private Integer roomsCount;
  private LocalDate checkInDate;
  private LocalDate checkOutDate;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private Payment payment;
  private BookingStatus bookingStatus;
  private Set<GuestDto> guests;

}
