package com.shaun.followservice.repo;

import com.shaun.followservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findFirstByEmail(String email);

    User findUserByEmail(String userEmail);
}