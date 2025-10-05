package com.project.learning.stayIn.repository;

import com.project.learning.stayIn.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
