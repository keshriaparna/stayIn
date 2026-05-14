package com.project.learning.stayIn.service;

import com.project.learning.stayIn.entity.User;
import org.springframework.stereotype.Service;

public interface UserService {

    User getUserById(Long id);
}
