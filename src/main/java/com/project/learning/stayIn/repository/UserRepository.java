package com.project.learning.stayIn.repository;

import com.project.learning.stayIn.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
