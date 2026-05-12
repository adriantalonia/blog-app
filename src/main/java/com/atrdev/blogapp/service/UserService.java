package com.atrdev.blogapp.service;

import com.atrdev.blogapp.entity.User;

import java.util.UUID;

public interface UserService {
    User getUserById(UUID userId);
}
