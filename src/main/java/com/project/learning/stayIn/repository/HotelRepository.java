package com.project.learning.stayIn.repository;

import com.project.learning.stayIn.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
}
