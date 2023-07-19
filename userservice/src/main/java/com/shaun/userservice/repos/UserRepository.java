package com.shaun.userservice.repos;

import com.shaun.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    User findUserById(Integer userId);

    User findUserByUsername(String username);

    User findFirstByUsername(String username);

    User findFirstByEmail(String email);

    User findUserByEmail(String userEmail);
}
