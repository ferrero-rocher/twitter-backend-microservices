package com.shaun.tweetservice.services;

import com.shaun.tweetservice.dto.LoginRequest;
import com.shaun.tweetservice.dto.LoginResponse;
import com.shaun.tweetservice.entity.User;
import com.shaun.tweetservice.repo.UserRepository;
import com.shaun.tweetservice.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@Service
public class LoginServiceImpl implements UserDetailsService,LoginService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private RestTemplate restTemplate;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //Write Logic to get the user from the DB
        User user = userRepository.findFirstByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User not found", null);
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());

    }

    @Override
    public String generateLoginToken() {


        String url = "http://USERSERVICE/api/userservice/auth/login";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        LoginRequest loginRequestPayload = new LoginRequest("service@gmail.com", "service");
        HttpEntity<LoginRequest> requestEntity = new HttpEntity<>(loginRequestPayload, headers);

        LoginResponse response = restTemplate.postForObject(url, requestEntity, LoginResponse.class);
        System.out.println(response.getJwt());

        return response.getJwt();
    }
}

