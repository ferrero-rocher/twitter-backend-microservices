package com.shaun.followservice.services;

import com.shaun.followservice.entity.User;
import com.shaun.followservice.entity.UserRelation;
import com.shaun.followservice.entity.UserRelationPk;
import com.shaun.followservice.repo.UserRelationRepository;
import com.shaun.followservice.repo.UserRelationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRelationServiceImpl implements UserRelationService {
    @Autowired
    UserRelationRepository userRelationRepository;

    @Override
    public void createRelation(int currentUserId, int followedId) {
        UserRelation userRelation =new UserRelation(new UserRelationPk(new User(currentUserId),new User(followedId)));
        userRelationRepository.save(userRelation);

    }

    @Override
    public void deleteRelation(int currentUserId, int unFollowId) {
        userRelationRepository.delete(new UserRelation(new UserRelationPk(new User(currentUserId),new User(unFollowId))));
    }
}
