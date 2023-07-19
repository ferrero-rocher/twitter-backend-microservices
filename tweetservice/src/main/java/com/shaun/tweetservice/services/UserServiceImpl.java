package com.shaun.tweetservice.services;

import com.shaun.tweetservice.dto.UserResponse;
import com.shaun.tweetservice.entity.User;
import com.shaun.tweetservice.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;
    @Autowired
    private RestTemplate restTemplate;


    @Override
    public User findUserByEmail(String userEmail) {
        User user = userRepository.findFirstByEmail(userEmail);
        return user;
    }

    @Override
    public boolean getCelebrityStatus(Integer id,String token) {

        System.out.println(token);
        String url = "http://USERSERVICE/api/user/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);



        HttpEntity<?> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<UserResponse> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, UserResponse.class);
        return  response.getBody().getIsCelebrity();

    }
}
