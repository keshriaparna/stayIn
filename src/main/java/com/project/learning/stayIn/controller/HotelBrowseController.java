package com.project.learning.stayIn.controller;

import com.project.learning.stayIn.dto.HotelDto;
import com.project.learning.stayIn.dto.HotelSearchRequest;
import com.project.learning.stayIn.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
public class HotelBrowseController {

  private final InventoryService inventoryService;

  @GetMapping("/search")
  public ResponseEntity<Page<HotelDto>> searchHotels(@RequestBody HotelSearchRequest hotelSearchRequest){
    Page<HotelDto> page = inventoryService.searchHotels(hotelSearchRequest);
    return ResponseEntity.ok(page);
  }

}
