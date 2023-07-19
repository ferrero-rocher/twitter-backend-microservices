package com.shaun.tweetservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shaun.tweetservice.dto.CreateTweetRequest;
import com.shaun.tweetservice.dto.TweetResponse;
import com.shaun.tweetservice.entity.Tweet;
import com.shaun.tweetservice.entity.User;
import com.shaun.tweetservice.errorhandler.NoRecordFoundException;
import com.shaun.tweetservice.repo.TweetsRepository;
import com.shaun.tweetservice.repo.UserRepository;
import com.shaun.tweetservice.services.TweetService;
import com.shaun.tweetservice.services.UserService;
import com.shaun.tweetservice.util.JwtUtil;
import io.lettuce.core.support.caching.RedisCache;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

@RestController
@ControllerAdvice
@RequestMapping("api/tweetservice")
public class TweetController {
    @Autowired
    TweetService tweetService;

    @Autowired
    private HashOperations<String, String, TweetResponse> hashOperations;

    @Autowired
    UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    TweetsRepository tweetsRepository;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private CacheManager cacheManager;

    @PostMapping("/tweet")
    public ResponseEntity<?> CreateTweet(@RequestBody CreateTweetRequest createTweetRequest, HttpServletRequest request)
    {
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);
        String userEmail = jwtUtil.extractUsername(token);
        //make call to user controller to see if hes a celebrity
        //if yes we store it to the cache

        User user  =userService.findUserByEmail(userEmail);
        boolean status = userService.getCelebrityStatus(user.getId(),token);

        tweetService.createTweet(createTweetRequest,user);

        return new ResponseEntity<>("Tweet posted Sucesfully...!!!", HttpStatus.CREATED);
    }

    @GetMapping("/tweet/{tweetId}")
        public ResponseEntity<?> getTweetByItsId(@PathVariable("tweetId") String tweetID)
        {

            Integer tweetId = Integer.valueOf(tweetID);
            var tweet = tweetService.getTweetByTweetId(tweetId);

            if(tweet==null)
            {
                throw  new NoRecordFoundException(tweetId + " not found");
            }
            return new ResponseEntity<>(tweet,HttpStatus.OK);
        }

    @GetMapping("/user/{userId}/tweet")
    public ResponseEntity<?> getAllUsersTweet(@PathVariable("userId") Integer userId)
    {
        var tweet = tweetService.getTweetByUserId(userId);
        //System.out.println(tweet);
       // System.out.println(tweetsRepository.findByUserId(userId));
        //Tweet tweet =tweetsRepository.findATweetByTweetId(tweetId);
        return new ResponseEntity<>(tweet,HttpStatus.OK);
    }

    @DeleteMapping("tweet/{tweetId}")
    public ResponseEntity<?> deletedATweet(@PathVariable("tweetId") Integer tweetId)
    {
        var tweet = tweetService.getTweetByTweetId(tweetId);

        if(tweet==null)
        {
            throw  new NoRecordFoundException(tweetId + " not found");
        }
        tweetService.deleteTweetbyTweetId(tweetId);
        return new ResponseEntity<>("tweet deleted succesfully",HttpStatus.OK);

    }

    @GetMapping("tweet/populartweets")
    public List<TweetResponse> getPopularTweets() {
        Set<String> cacheKeys = redisTemplate.keys("popularTweets::*");
        System.out.println(Arrays.toString(cacheKeys.stream().toArray()));
        List<TweetResponse> popularTweets = new ArrayList<>();

        for (String cacheKey : cacheKeys) {
            Object tweetObject = redisTemplate.opsForValue().get(cacheKey);
            System.out.println(tweetObject.toString());
            if (tweetObject instanceof TweetResponse) {
                TweetResponse tweet = (TweetResponse) tweetObject;
                popularTweets.add(tweet);
            }
        }

        return popularTweets;
    }


}
