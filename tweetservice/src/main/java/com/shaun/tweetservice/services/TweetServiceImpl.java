package com.shaun.tweetservice.services;

import com.shaun.tweetservice.dto.CreateTweetRequest;
import com.shaun.tweetservice.dto.TweetResponse;
import com.shaun.tweetservice.entity.Tweet;
import com.shaun.tweetservice.entity.User;
import com.shaun.tweetservice.repo.TweetsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TweetServiceImpl implements TweetService {

    @Autowired
    TweetsRepository tweetsRepository;
    @Autowired
    private CacheManager cacheManager;


    @Override
    public void createTweet(CreateTweetRequest createTweetRequest, User currentUser) {
        Calendar calendar=Calendar.getInstance();

        //if(!user.isPresent()) return;
        Tweet tweet = Tweet.builder()
                .text(createTweetRequest.getText())
                .user(currentUser)
                .retweetsCount(0)
                .likeCount(0)
                .createdAt(new Date(calendar.getTime().getTime()))
                .build();

        tweetsRepository.save(tweet);
    }

    @Override
    @Cacheable(value = "popularTweets", unless = "#result == null || !#result.popular")
    public TweetResponse getTweetByTweetId(Integer tweetId) {
        Optional<Tweet> tweet =  tweetsRepository.findTweetByTweetId(tweetId);
        TweetResponse tweetResponse = tweet.map(this::convertToTweetResponse ).orElse(null);
        return tweetResponse;

    }

    @Override

    public List<TweetResponse> getTweetByUserId(Integer userId) {
        Optional<List<Tweet>> tweetsList =  tweetsRepository.findAllByUserId(userId);
        List<TweetResponse> tweetResponseList = tweetsList.orElse(Collections.emptyList()).stream().map(this::convertToTweetResponse).collect(Collectors.toList());
        return tweetResponseList;
    }

    @Override
    public void deleteTweetbyTweetId(Integer tweetId) {
        Optional<Tweet> tweetOptional =tweetsRepository.findById(tweetId);
        if(tweetOptional.isPresent())
        {
            Tweet t =tweetOptional.get();
            t.setDeleted(true);
            tweetsRepository.save(t);
        }
    }

    private TweetResponse convertToTweetResponse(Tweet t) {
        return TweetResponse.builder()
                .text(t.getText())
                .createdAt(t.getCreatedAt())
                .likeCount(t.getLikeCount())
                .retweetsCount(t.getRetweetsCount())
                .userId(t.getUser().getId())
                .isPopular(t.isPopular() || t.getUser().isCelebrity())
                .build();
    }


}
