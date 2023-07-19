package com.shaun.userservice.services;

import com.shaun.userservice.dto.RegisterUserRequest;
import com.shaun.userservice.dto.RegisteredUserResponse;
import com.shaun.userservice.dto.UserResponse;
import com.shaun.userservice.entity.Token;
import com.shaun.userservice.entity.User;
import com.shaun.userservice.models.TokenType;
import com.shaun.userservice.repos.TokenRepository;
import com.shaun.userservice.repos.UserRepository;
import com.shaun.userservice.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenRepository tokenRepository;
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private LoginServiceImpl loginService;

    @Override
    public UserResponse findById(Integer userId) {

        User user = userRepository.findUserById(userId);
        if (user == null) return null;
        UserResponse userResponse = UserResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .phone(user.getPhone())
                .fullname(user.getFullname())
                .createdAt(user.getCreatedAt())
                .isCelebrity(user.isCelebrity())
                .build();
        return userResponse;
    }

    @Override
    public UserResponse findByUsername(String username) {
        User user = userRepository.findUserByUsername(username);
        if (user == null) return null;
        UserResponse userResponse = UserResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .phone(user.getPhone())
                .fullname(user.getFullname())
                .createdAt(user.getCreatedAt())
                .isCelebrity(user.isCelebrity())
                .build();
        return userResponse;

    }



    @Override
    public RegisteredUserResponse createUser(RegisterUserRequest registerUserRequest) {
        Calendar calendar = Calendar.getInstance();
         User user = User.builder()
                .username(registerUserRequest.getUsername())
                .email(registerUserRequest.getEmail())
                .password(encoder.encode(registerUserRequest.getPassword()))
                .fullname(registerUserRequest.getFullname())
                .phone(registerUserRequest.getPhone())
                .createdAt(new Date(calendar.getTime().getTime()))
                .build();
        userRepository.save(user);
        final UserDetails userDetails = loginService.loadUserByUsername(registerUserRequest.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());
        RegisteredUserResponse registeredUserResponse = new RegisteredUserResponse(jwt);

        var token = Token.builder()
                .user(user)
                .token(jwt)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .build();
        tokenRepository.save(token);
        return registeredUserResponse;
    }

    @Override
    public int findByEmail(String userEmail) {
        User user = userRepository.findUserByEmail(userEmail);
        return user.getId();
    }





}


