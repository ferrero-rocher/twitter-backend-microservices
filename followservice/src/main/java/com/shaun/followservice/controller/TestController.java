package com.shaun.followservice.controller;

import com.shaun.followservice.entity.User;
import com.shaun.followservice.entity.UserRelation;
import com.shaun.followservice.entity.UserRelationPk;
import com.shaun.followservice.repo.UserRelationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    UserRelationRepository userRelationRepository;
    @GetMapping("/relation")
    public String createRelation()
    {

        UserRelation userRelation1 =new UserRelation(new UserRelationPk(new User(1),new User(2)));
        UserRelation userRelation2 =new UserRelation(new UserRelationPk(new User(1),new User(3)));

        try {
            userRelationRepository.save(userRelation1);
            userRelationRepository.save(userRelation2);
        }
        catch (Exception ex)
        {
            return "Cannot follow as user does not exist";
        }


        return "Relation created succesfully";
    }
}
