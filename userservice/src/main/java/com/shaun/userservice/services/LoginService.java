package com.shaun.userservice.services;

import com.shaun.userservice.dto.LoginRequest;
import com.shaun.userservice.dto.LoginResponse;
import org.springframework.security.core.userdetails.UserDetails;

public interface LoginService {
    public UserDetails loadUserByUsername(String email);

    LoginResponse authenticate(LoginRequest loginRequest);

    void invalidateToken(String token);
}
