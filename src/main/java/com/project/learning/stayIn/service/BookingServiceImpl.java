package com.project.learning.stayIn.service;

import com.project.learning.stayIn.dto.BookingDto;
import com.project.learning.stayIn.dto.BookingRequestDto;
import com.project.learning.stayIn.entity.*;
import com.project.learning.stayIn.entity.enums.BookingStatus;
import com.project.learning.stayIn.exception.ResourceNotFoundException;
import com.project.learning.stayIn.repository.BookingRepository;
import com.project.learning.stayIn.repository.HotelRepository;
import com.project.learning.stayIn.repository.InventoryRepository;
import com.project.learning.stayIn.repository.RoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService{

    private BookingRepository bookingRepository;
    private HotelRepository hotelRepository;
    private RoomRepository roomRepository;
    private InventoryRepository inventoryRepository;
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public BookingDto initializeBooking(BookingRequestDto bookingRequestDto) {
        log.info("Initializing booking for hotel : {}, room; {}, date: {}-{}",bookingRequestDto.getHotelId(),
                bookingRequestDto.getRoomId(),bookingRequestDto.getCheckInDate(),bookingRequestDto.getCheckOutDate());
        Hotel hotel = hotelRepository.findById(bookingRequestDto.getHotelId())
                .orElseThrow(()->new ResourceNotFoundException("Hotel not found with id: "+bookingRequestDto.getHotelId()));
        Room room = roomRepository.findById(bookingRequestDto.getRoomId())
                .orElseThrow(()->new ResourceNotFoundException("Room not found with id: "+bookingRequestDto.getRoomId()));
        List<Inventory> inventoryList = inventoryRepository.findAndLockAvailableInventory(room.getId(),
                bookingRequestDto.getCheckInDate(),bookingRequestDto.getCheckOutDate(),bookingRequestDto.getRoomsCount());
        long daysCount = ChronoUnit.DAYS.between(bookingRequestDto.getCheckInDate(),bookingRequestDto.getCheckOutDate())+1;
        if(inventoryList.size()!= daysCount){
            throw new IllegalStateException("Room is not available anymore");
        }
        //reserve the room/update the booked count of inventories
        for(Inventory inventory: inventoryList){
            inventory.setBookedCount(inventory.getBookedCount() + bookingRequestDto.getRoomsCount());
        }
        inventoryRepository.saveAll(inventoryList);

        //Create the booking
        User user = new User();
        user.setId(1L); //TODO:Remove dummy User

        //TODO: Calculate dynamic amount

        Booking booking = Booking.builder()
                .bookingStatus(BookingStatus.RESERVED)
                .hotel(hotel)
                .room(room)
                .checkInDate(bookingRequestDto.getCheckInDate())
                .checkOutDate(bookingRequestDto.getCheckOutDate())
                .user(user)
                .roomsCount(bookingRequestDto.getRoomsCount())
                .amount(BigDecimal.TEN)
                .build();
        booking = bookingRepository.save(booking);
        return modelMapper.map(booking,BookingDto.class);
    }
}
