package com.shaun.tweetservice.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    private String username;

    private String email;

    private String password;

    private String fullname;

    private String phone;

    private boolean isCelebrity=false;

    private Date createdAt=calculatime();

    private Date updatedAt;
    @OneToMany(mappedBy = "user")
    private List<Token> tokens;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Tweet> tweets;

    @ManyToMany
    @JoinTable(
            name = "likes",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "tweet_id")
    )
    private List<Tweet> likedTweets;

    @ManyToMany
    @JoinTable(
            name = "retweets",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "tweet_id")
    )
    private List<Tweet> retweetedTweets;



    private Date calculatime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        return new Date(calendar.getTime().getTime());
    }

    public User(int id)
    {
        this.id=id;
    }

    public void likeTweet(Integer tweetId) {
        // Create a new Tweet object with the given tweetId
        Tweet tweet = new Tweet();
        tweet.setTweetId(tweetId);

        if (likedTweets == null) {
            likedTweets = new ArrayList<>();
        }
        likedTweets.add(tweet);
    }


}

