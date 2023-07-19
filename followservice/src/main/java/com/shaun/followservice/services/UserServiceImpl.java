package com.shaun.followservice.services;

import com.shaun.followservice.dto.UserResponse;
import com.shaun.followservice.entity.User;
import com.shaun.followservice.entity.UserRelation;
import com.shaun.followservice.repo.TokenRepository;
import com.shaun.followservice.repo.UserRelationRepository;
import com.shaun.followservice.repo.UserRepository;
import com.shaun.followservice.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserRelationRepository userRelationRepository;
    @Autowired
    TokenRepository tokenRepository;
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private LoginServiceImpl loginService;



    @Override
    public User findUserByEmail(String userEmail) {
        User user = userRepository.findFirstByEmail(userEmail);
        return user;
    }
    @Override
    public List<UserResponse> getFollowersById(Integer userId)
    {
        //System.out.println(userRelationRepository.findByUserRelationPkUser2Id(userId));
        //List of Userrelation
        return userRelationRepository.findByUserRelationPkUser2Id(userId).stream().map(this::mapFollowersToUserResponse).toList();

    }

    @Override
    public List<UserResponse> getFollowingsById(Integer userId) {
        return userRelationRepository.findByUserRelationPkUser1Id(userId).stream().map(this::mapFollowwingToUserResponse).toList();
    }
    private UserResponse mapFollowwingToUserResponse(UserRelation userRelation) {
        return UserResponse.builder()
                .username(userRelation.getUserRelationPk().getUser2().getUsername())
                .email(userRelation.getUserRelationPk().getUser2().getEmail())
                .phone(userRelation.getUserRelationPk().getUser2().getPhone())
                .fullname(userRelation.getUserRelationPk().getUser2().getFullname())
                .createdAt(userRelation.getUserRelationPk().getUser2().getCreatedAt())
                .isCelebrity(userRelation.getUserRelationPk().getUser2().isCelebrity())
                .build();

    }

    public UserResponse mapFollowersToUserResponse(UserRelation userRelation)
    {
        return UserResponse.builder()
                .username(userRelation.getUserRelationPk().getUser1().getUsername())
                .email(userRelation.getUserRelationPk().getUser1().getEmail())
                .phone(userRelation.getUserRelationPk().getUser1().getPhone())
                .fullname(userRelation.getUserRelationPk().getUser1().getFullname())
                .createdAt(userRelation.getUserRelationPk().getUser1().getCreatedAt())
                .isCelebrity(userRelation.getUserRelationPk().getUser1().isCelebrity())
                .build();
    }
    @Override
    public int findByEmail(String userEmail) {
        User user = userRepository.findUserByEmail(userEmail);
        return user.getId();
    }

}
