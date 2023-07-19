package com.shaun.tweetservice.services;

import com.shaun.tweetservice.dto.CreateTweetRequest;
import com.shaun.tweetservice.dto.TweetResponse;
import com.shaun.tweetservice.entity.Tweet;
import com.shaun.tweetservice.entity.User;

import java.util.List;
import java.util.Optional;

public interface TweetService {




    void createTweet(CreateTweetRequest createTweetRequestm , User user);

    TweetResponse getTweetByTweetId(Integer tweetId);


    List<TweetResponse> getTweetByUserId(Integer userId);

    void deleteTweetbyTweetId(Integer tweetId);
}
