package com.shaun.tweetservice.repo;

import com.shaun.tweetservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findFirstByEmail(String email);

    User findUserByEmail(String userEmail);
}