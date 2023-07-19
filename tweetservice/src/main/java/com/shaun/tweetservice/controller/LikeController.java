package com.shaun.tweetservice.controller;

import com.shaun.tweetservice.config.MQConfig;
import com.shaun.tweetservice.dto.LoginRequest;
import com.shaun.tweetservice.dto.LoginResponse;
import com.shaun.tweetservice.dto.Notification;
import com.shaun.tweetservice.dto.UserResponse;
import com.shaun.tweetservice.entity.Tweet;
import com.shaun.tweetservice.entity.User;
import com.shaun.tweetservice.repo.TweetsRepository;
import com.shaun.tweetservice.repo.UserRepository;
import com.shaun.tweetservice.services.LoginService;
import com.shaun.tweetservice.services.TweetService;
import com.shaun.tweetservice.services.UserService;
import com.shaun.tweetservice.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

@RestController
@ControllerAdvice
@RequestMapping("api/tweetservice")
public class LikeController {
    @Autowired
    TweetService tweetService;

    @Autowired
    UserService userService;

    @Autowired
    TweetsRepository tweetsRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    LoginService loginService;
    @Autowired
    private RabbitTemplate template;

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private RestTemplate restTemplate;



    @PostMapping("/tweet/{tweetId}/like")
    public ResponseEntity<?> likeATweet(HttpServletRequest request, @PathVariable("tweetId") Integer tweetId)
    {
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);
        String userEmail = jwtUtil.extractUsername(token);

        User currentUser =   userRepository.findUserByEmail(userEmail);
        int currentUserId = currentUser.getId();





        Optional<User> userOptional = userRepository.findById(currentUserId);
        Optional<Tweet> tweetOptional = tweetsRepository.findById(tweetId);
        if(!tweetOptional.isPresent())
        {
            return new ResponseEntity<>("Tweet does not exist", HttpStatus.BAD_REQUEST);
        }

        if (userOptional.isPresent() ) {
            User user = userOptional.get();
            Tweet tweet = tweetOptional.get();
            if (user.getLikedTweets().contains(tweet)) {
                // Handle case when user already liked the tweet
                return new ResponseEntity<>("Already liked", HttpStatus.OK);
            }
            if (user.getLikedTweets() == null) {
                user.setLikedTweets(new ArrayList<>());
            }

            user.getLikedTweets().add(tweet);
            userRepository.save(user);
            tweet.setLikeCount(tweet.getLikeCount()+1);
            tweetsRepository.save(tweet);
        }
        Notification notification = new Notification("User "+currentUserId+" liked tweet No "+tweetId);
        template.convertAndSend(MQConfig.EXCHANGE,
                MQConfig.ROUTING_KEY, notification);

        //logic to send message to MQ
        return new ResponseEntity<>("Tweet liked Sucessfully...!!!", HttpStatus.CREATED);
    }

    @PostMapping("/tweet/{tweetId}/unlike")
    public ResponseEntity<?> unLikeATweet(HttpServletRequest request, @PathVariable("tweetId") Integer tweetId)
    {
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);
        String userEmail = jwtUtil.extractUsername(token);

        User currentUser =   userRepository.findUserByEmail(userEmail);
        int currentUserId = currentUser.getId();




        Optional<User> userOptional = userRepository.findById(currentUserId);
        Optional<Tweet> tweetOptional = tweetsRepository.findById(tweetId);
        if(!tweetOptional.isPresent())
        {
            return new ResponseEntity<>("Tweet does not exist", HttpStatus.BAD_REQUEST);
        }

        if (userOptional.isPresent() ) {
            User user = userOptional.get();
            Tweet tweet = tweetOptional.get();
            if (user.getLikedTweets().contains(tweet)) {
                // Handle case when user already liked the tweet
                user.getLikedTweets().remove(tweet);
                tweet.setLikeCount(tweet.getLikeCount()-1);
                tweetsRepository.save(tweet);
                return new ResponseEntity<>("Unliked Succesfully", HttpStatus.OK);
            }



        }



        return new ResponseEntity<>("Need to like Tweet first", HttpStatus.BAD_REQUEST);
    }


}
