package com.shaun.tweetservice.services;

import com.shaun.tweetservice.entity.User;

import java.util.Optional;

public interface UserService {
    User findUserByEmail(String userEmail);

    boolean getCelebrityStatus(Integer id,String token);
}
