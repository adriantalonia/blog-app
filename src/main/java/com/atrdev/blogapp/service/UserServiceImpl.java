package com.atrdev.blogapp.service;

import com.atrdev.blogapp.entity.User;
import com.atrdev.blogapp.exception.ResourceNotFoundException;
import com.atrdev.blogapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getUserById(UUID userId) {
        return userRepository
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));
    }
}
