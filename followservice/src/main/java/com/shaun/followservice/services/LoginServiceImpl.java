package com.shaun.followservice.services;

import com.shaun.followservice.entity.User;
import com.shaun.followservice.repo.UserRepository;
import com.shaun.followservice.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class LoginServiceImpl implements UserDetailsService,LoginService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //Write Logic to get the user from the DB
        User user = userRepository.findFirstByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User not found", null);
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());

    }
}

