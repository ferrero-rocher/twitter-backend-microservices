package com.shaun.followservice.services;

import org.springframework.security.core.userdetails.UserDetails;

public interface LoginService {
    public UserDetails loadUserByUsername(String email);



}