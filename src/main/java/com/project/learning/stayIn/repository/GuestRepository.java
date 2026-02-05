package com.project.learning.stayIn.repository;

import com.project.learning.stayIn.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest, Long> {
}
