package com.project.learning.stayIn.dto;

import com.project.learning.stayIn.entity.Hotel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelPriceDto {
    private Hotel hotel;
    private Double price;
}
