package com.shaun.tweetservice.repo;

import com.shaun.tweetservice.entity.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TweetsRepository extends JpaRepository<Tweet,Integer> {


    Optional<List<Tweet>> findAllByUserId(Integer userId);


    @Query("""
            select t from Tweet t where t.tweetId=:tweetId and t.isDeleted=false
            """)
    Optional<Tweet> findTweetByTweetId(Integer tweetId);
}
