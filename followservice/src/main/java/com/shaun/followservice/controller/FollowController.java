package com.shaun.followservice.controller;

import com.shaun.followservice.dto.UserResponse;
import com.shaun.followservice.errorhandler.NoRecordFoundException;
import com.shaun.followservice.services.LoginService;
import com.shaun.followservice.services.UserRelationService;
import com.shaun.followservice.services.UserService;
import com.shaun.followservice.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@ControllerAdvice
@RequestMapping("api/followservice")
public class FollowController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserService userService;
    @Autowired
    private UserRelationService userRelation;


    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/user/{userId}/followers")
    public ResponseEntity<?> getFollowers(@PathVariable("userId")Integer userId)
    {
        List<UserResponse> userResponseList= userService.getFollowersById(userId);
        return new ResponseEntity<>(userResponseList, HttpStatus.OK);


    }
    @GetMapping("/user/{userId}/following")
    public ResponseEntity<?> getFollowing(@PathVariable("userId")Integer userId)
    {
        List<UserResponse> userResponseList= userService.getFollowingsById(userId);
        return new ResponseEntity<>(userResponseList,HttpStatus.OK);


    }
    @PostMapping("/user/{userId}/follow")
    public ResponseEntity<?> followUser(@PathVariable("userId")Integer userId, HttpServletRequest request)
    {
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);
        String userEmail = jwtUtil.extractUsername(token);
        int currentUserId =  userService.findByEmail(userEmail);
        int followedId=userId;
        if(followedId==currentUserId)
        {
            return new ResponseEntity<>("Cannot follow oneself",HttpStatus.BAD_REQUEST);
        }
        try
        {
            userRelation.createRelation(currentUserId,followedId);
        }
        catch (Exception ex)
        {
            throw  new NoRecordFoundException(userId + " not found");
        }


        return new ResponseEntity<>("Followed "+ followedId +" successfully",HttpStatus.OK);
    }

    @PostMapping("/user/{userId}/unfollow")
    public ResponseEntity<?> unFollowUser(@PathVariable("userId")Integer userId, HttpServletRequest request)
    {
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);
        String userEmail = jwtUtil.extractUsername(token);
        int currentUserId =  userService.findByEmail(userEmail);
        int unFollowId=userId;
        if(unFollowId==currentUserId)
        {
            return new ResponseEntity<>("Cannot unfollow oneself",HttpStatus.BAD_REQUEST);
        }
        try
        {
            userRelation.deleteRelation(currentUserId,unFollowId);
        }
        catch (Exception ex)
        {
            throw  new NoRecordFoundException(userId + " not found");
        }


        return new ResponseEntity<>("UnFollowed "+ unFollowId +" successfully",HttpStatus.OK);
    }


}
