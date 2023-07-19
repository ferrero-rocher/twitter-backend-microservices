package com.shaun.tweetservice.controller;

import com.shaun.tweetservice.config.MQConfig;
import com.shaun.tweetservice.dto.LoginRequest;
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

import java.util.ArrayList;
import java.util.Optional;

@RestController
@ControllerAdvice
@RequestMapping("api/tweetservice")
public class RetweetController {
    @Autowired
    TweetService tweetService;

    @Autowired
    UserService userService;

    @Autowired
    TweetsRepository tweetsRepository;
    @Autowired
     RestTemplate restTemplate;
    @Autowired
    private RabbitTemplate template;

    @Autowired
    UserRepository userRepository;
    @Autowired
    LoginService loginService;


    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/tweet/{tweetId}/retweet")
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


            User user = userOptional.get();
            Tweet tweet = tweetOptional.get();
            if (user.getRetweetedTweets().contains(tweet)) {
                // Handle case when user already liked the tweet
                return new ResponseEntity<>("Already retweeted", HttpStatus.OK);
            }
            if (user.getRetweetedTweets() == null) {
                user.setRetweetedTweets(new ArrayList<>());
            }

            user.getRetweetedTweets().add(tweet);
            userRepository.save(user);
            tweet.setLikeCount(tweet.getRetweetsCount()+1);
            tweetsRepository.save(tweet);

        Notification notification = new Notification("User "+currentUserId+" retweeted tweet No "+tweetId);

        template.convertAndSend(MQConfig.EXCHANGE,
                MQConfig.ROUTING_KEY, notification);

        return new ResponseEntity<>("retweet successfully", HttpStatus.CREATED);
    }
}
