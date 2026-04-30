package com.atrdev.blogapp.service;

import org.springframework.security.core.userdetails.UserDetails;

public class AuthenticationServiceImpl implements AuthenticationService {
    @Override
    public UserDetails authenticate(String email, String password) {
        return null;
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        return "";
    }
}
