package com.project.learning.stayIn.service;

import com.project.learning.stayIn.entity.User;
import com.project.learning.stayIn.exception.ResourceNotFoundException;
import com.project.learning.stayIn.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not found with id: "+id));
    }
}
