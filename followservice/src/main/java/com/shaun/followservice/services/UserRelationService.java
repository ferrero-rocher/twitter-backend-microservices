package com.shaun.followservice.services;

public interface UserRelationService {
    void createRelation(int currentUserId, int followedId);

    void deleteRelation(int currentUserId, int unFollowId);
}
