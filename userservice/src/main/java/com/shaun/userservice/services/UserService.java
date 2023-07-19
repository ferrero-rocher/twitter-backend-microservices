package com.shaun.userservice.services;

import com.shaun.userservice.dto.LoginRequest;
import com.shaun.userservice.dto.RegisterUserRequest;
import com.shaun.userservice.dto.RegisteredUserResponse;
import com.shaun.userservice.dto.UserResponse;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserResponse findById(Integer userId);

    UserResponse findByUsername(String username);




    RegisteredUserResponse createUser(RegisterUserRequest registerUserRequest);

    int findByEmail(String userEmail);


}
