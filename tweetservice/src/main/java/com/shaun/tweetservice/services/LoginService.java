package com.shaun.tweetservice.services;

import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;

public interface LoginService {
    public UserDetails loadUserByUsername(String email);


    String generateLoginToken();
}