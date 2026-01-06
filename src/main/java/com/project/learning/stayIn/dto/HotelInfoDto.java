package com.project.learning.stayIn.dto;

import com.project.learning.stayIn.entity.Hotel;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HotelInfoDto {

  private HotelDto hotel;
  private List<RoomDto> rooms;

}
