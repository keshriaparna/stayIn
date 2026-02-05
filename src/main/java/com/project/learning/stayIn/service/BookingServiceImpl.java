package com.project.learning.stayIn.service;

import com.project.learning.stayIn.dto.BookingDto;
import com.project.learning.stayIn.dto.BookingRequestDto;
import com.project.learning.stayIn.dto.GuestDto;
import com.project.learning.stayIn.entity.*;
import com.project.learning.stayIn.entity.enums.BookingStatus;
import com.project.learning.stayIn.exception.ResourceNotFoundException;
import com.project.learning.stayIn.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.misc.LogManager;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService{

    private final BookingRepository bookingRepository;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final InventoryRepository inventoryRepository;
    private final ModelMapper modelMapper;
    private final GuestRepository guestRepository;

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
        //reserve the room/update the reserved count in inventories
        for(Inventory inventory: inventoryList){
            inventory.setReservedCount(inventory.getReservedCount() + bookingRequestDto.getRoomsCount());
        }
        inventoryRepository.saveAll(inventoryList);

        //Create the booking

        //TODO: Calculate dynamic amount

        Booking booking = Booking.builder()
                .bookingStatus(BookingStatus.RESERVED)
                .hotel(hotel)
                .room(room)
                .checkInDate(bookingRequestDto.getCheckInDate())
                .checkOutDate(bookingRequestDto.getCheckOutDate())
                .user(getCurrentUser())
                .roomsCount(bookingRequestDto.getRoomsCount())
                .amount(BigDecimal.TEN)
                .build();
        booking = bookingRepository.save(booking);
        return modelMapper.map(booking,BookingDto.class);
    }

    @Override
    public BookingDto addGuests(Long bookingId, List<GuestDto> guestDtoList) {
        log.info("Adding guests for booking with id: {}", bookingId);
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(()->
                new ResourceNotFoundException("Booking not found with id:" +bookingId));
        if(hasBookingExpired(booking)){
            throw new IllegalStateException("Booking has already expired");
        }
        if(booking.getBookingStatus()!= BookingStatus.RESERVED){
            throw new IllegalStateException("Booking is not under reserved state, cannot add guests");
        }
        for(GuestDto guestDto: guestDtoList){
            Guest guest = modelMapper.map(guestDto, Guest.class);
            guest.setUser(getCurrentUser());
            guest = guestRepository.save(guest);
            booking.getGuests().add(guest);
        }
        booking.setBookingStatus(BookingStatus.GUESTS_ADDED);
        booking = bookingRepository.save(booking);
        return modelMapper.map(booking, BookingDto.class);
    }
    public boolean hasBookingExpired(Booking booking){
        return booking.getCreatedAt().plusMinutes(10).isBefore(LocalDateTime.now());
    }
    public User getCurrentUser(){
        User user = new User();
        user.setId(1L); //TODO:Remove dummy User
        return user;
    }
}
