package com.shaun.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TweetResponse implements Serializable {
    private String text;

    private Date createdAt;

    private Integer likeCount;

    private Integer retweetsCount;

    private Integer userId;
    private boolean isPopular;


}
