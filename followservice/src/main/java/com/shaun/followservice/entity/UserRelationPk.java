package com.shaun.followservice.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class UserRelationPk implements Serializable {


    @ManyToOne
    @JoinColumn(name = "userId",nullable = false)

    private User user1;

    @ManyToOne
    @JoinColumn(name = "followedId",nullable = false)
    private User user2;






}
