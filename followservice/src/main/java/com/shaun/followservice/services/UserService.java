package com.shaun.followservice.services;

import com.shaun.followservice.dto.UserResponse;
import com.shaun.followservice.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User findUserByEmail(String userEmail);
    List<UserResponse> getFollowersById(Integer userId);

    List<UserResponse> getFollowingsById(Integer userId);
    int findByEmail(String userEmail);

}
