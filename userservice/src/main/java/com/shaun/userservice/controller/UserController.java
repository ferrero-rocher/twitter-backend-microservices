package com.shaun.userservice.controller;


import com.shaun.userservice.dto.RegisterUserRequest;
import com.shaun.userservice.dto.RegisteredUserResponse;
import com.shaun.userservice.dto.TweetResponse;
import com.shaun.userservice.dto.UserResponse;
import com.shaun.userservice.errorhandler.HttpClientErrorException$BadRequest;
import com.shaun.userservice.errorhandler.NoRecordFoundException;
import com.shaun.userservice.services.LoginService;
import com.shaun.userservice.services.UserService;
import com.shaun.userservice.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
@ControllerAdvice
@RequestMapping("api")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    LoginService loginService;
    @Autowired
    private RestTemplate restTemplate;



    @Autowired
    private JwtUtil jwtUtil;






    @GetMapping("/user/{userId}")
    public ResponseEntity <?> getUserById(@PathVariable("userId") Integer userId)
    {
       UserResponse userResponse = userService.findById(userId);
       if(userResponse==null) {
           throw  new NoRecordFoundException(userId + " not found");
       }
       return new ResponseEntity<>(userResponse,HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserByUsername(@RequestParam("username") String username)
    {
        UserResponse userResponse = userService.findByUsername(username);
        if(userResponse==null) {
            throw  new NoRecordFoundException(username + " not found");
        }
        return new ResponseEntity<>(userResponse,HttpStatus.OK);
    }
    @GetMapping("/user/{userId}/followers")
    public ResponseEntity<?> getFollowers(@PathVariable("userId")Integer userId,HttpServletRequest request)
    {
//        List<UserResponse> userResponseList= userService.getFollowersById(userId);
//        return new ResponseEntity<>(userResponseList, HttpStatus.OK);
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);
        System.out.println(token);
        String url = "http://FOLLOWSERVICE/api/followservice/user/" + userId +"/followers";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);



        HttpEntity<?> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<UserResponse[]> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, UserResponse[].class);


        return new ResponseEntity<>(response.getBody(), response.getStatusCode());

    }

    @GetMapping("/user/{userId}/following")
    public ResponseEntity<?> getFollowing(@PathVariable("userId")Integer userId,HttpServletRequest request)
    {
//        List<UserResponse> userResponseList= userService.getFollowersById(userId);
//        return new ResponseEntity<>(userResponseList, HttpStatus.OK);
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);
        System.out.println(token);
        String url = "http://FOLLOWSERVICE/api/followservice/user/" + userId +"/following";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);



        HttpEntity<?> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<UserResponse[]> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, UserResponse[].class);


        return new ResponseEntity<>(response.getBody(), response.getStatusCode());

    }

    @PostMapping("/user/{userId}/follow")
    public ResponseEntity<?> followUser(@PathVariable("userId")Integer userId,HttpServletRequest request) throws HttpClientErrorException$BadRequest {
//        List<UserResponse> userResponseList= userService.getFollowersById(userId);
//        return new ResponseEntity<>(userResponseList, HttpStatus.OK);
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);
        System.out.println(token);
        String url = "http://FOLLOWSERVICE/api/followservice/user/" + userId +"/follow";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);



        HttpEntity<?> requestEntity = new HttpEntity<>(headers);
        try
        {
            ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
            return new ResponseEntity<>(response.getBody(), response.getStatusCode());
        }
        catch (HttpClientErrorException.BadRequest ex)
        {
            return ResponseEntity.badRequest().body(ex.getResponseBodyAsString());
        }


    }
    @PostMapping("/user/{userId}/unfollow")
    public ResponseEntity<?> unFollowUser(@PathVariable("userId")Integer userId,HttpServletRequest request) throws HttpClientErrorException$BadRequest {
//        List<UserResponse> userResponseList= userService.getFollowersById(userId);
//        return new ResponseEntity<>(userResponseList, HttpStatus.OK);
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);
        System.out.println(token);
        String url = "http://FOLLOWSERVICE/api/followservice/user/" + userId +"/unfollow";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);



        HttpEntity<?> requestEntity = new HttpEntity<>(headers);
        try
        {
            ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
            return new ResponseEntity<>(response.getBody(), response.getStatusCode());
        }
        catch (HttpClientErrorException.BadRequest ex)
        {
            return ResponseEntity.badRequest().body(ex.getResponseBodyAsString());
        }


    }
    @GetMapping("/user/{userId}/tweets")
    public ResponseEntity<?> getAllTweets(@PathVariable("userId")Integer userId,HttpServletRequest request)
    {
//        List<UserResponse> userResponseList= userService.getFollowersById(userId);
//        return new ResponseEntity<>(userResponseList, HttpStatus.OK);
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);
        System.out.println(token);
        String url = "http://TWEETSERVICE/api/tweetservice/user/" + userId +"/tweet";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);



        HttpEntity<?> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<TweetResponse[]> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, TweetResponse[].class);


        return new ResponseEntity<>(response.getBody(), response.getStatusCode());

    }







}
