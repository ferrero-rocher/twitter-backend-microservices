package com.shaun.tweetservice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="tweet")
public class Tweet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@Column(name = "twee")
    private Integer tweetId;

    private String text;

    private Date createdAt=calculatime();

    private Integer likeCount;

    private Integer retweetsCount;

    private boolean isPopular=false;

    private boolean isDeleted=false;

    @ManyToOne
    @JoinColumn(name = "userId",nullable = false)
    @JsonBackReference
    private User user;

    @ManyToMany(mappedBy = "likedTweets")
    private List<User> likedByUsers;
    @ManyToMany(mappedBy = "retweetedTweets")
    private List<User> retweetedByUsers;





    private Date calculatime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        return new Date(calendar.getTime().getTime());
    }

    public Tweet(int id)
    {
        this.tweetId=id;
    }
}
