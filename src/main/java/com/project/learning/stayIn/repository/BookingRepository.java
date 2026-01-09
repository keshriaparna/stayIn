package com.project.learning.stayIn.repository;

import com.project.learning.stayIn.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking,Long> {

}
