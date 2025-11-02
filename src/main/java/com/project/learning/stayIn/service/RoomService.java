package com.project.learning.stayIn.service;

import com.project.learning.stayIn.dto.RoomDto;
import java.util.List;

public interface RoomService {

  RoomDto createNewRoom(Long HotelId, RoomDto roomDto);

  List<RoomDto> getAllRoomsInHotel(Long HotelId);

  RoomDto getRoomById(Long roomId);

  void deleteRoomById(Long roomId);

}
