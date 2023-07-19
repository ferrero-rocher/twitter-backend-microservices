package com.shaun.userservice.services;

import com.shaun.userservice.dto.LoginRequest;
import com.shaun.userservice.dto.LoginResponse;
import com.shaun.userservice.entity.Token;
import com.shaun.userservice.entity.User;
import com.shaun.userservice.models.TokenType;
import com.shaun.userservice.repos.TokenRepository;
import com.shaun.userservice.repos.UserRepository;
import com.shaun.userservice.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
public class LoginServiceImpl implements UserDetailsService,LoginService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //Write Logic to get the user from the DB
        User user = userRepository.findFirstByEmail(email);

        if(user == null){
            throw new UsernameNotFoundException("User not found",null);
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }

    @Override
    public LoginResponse authenticate(LoginRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));
        final UserDetails userDetails = loadUserByUsername(loginRequest.getEmail());
        final User user= userRepository.findFirstByEmail(loginRequest.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());
        var token = Token.builder()
                .user(user)
                .token(jwt)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .build();
        revokeAllUserTokens(user);
        tokenRepository.save(token);

        return new LoginResponse(jwt);
    }

    @Override
    public void invalidateToken(String token) {
        var validToken = tokenRepository.findByToken(token);
        validToken.map(t -> {
            t.setRevoked(true);
            t.setExpired(true);
            tokenRepository.save(t);
             return t;
        });
    }

    private void revokeAllUserTokens(User user)
    {
        var validToken= tokenRepository.findAllValidTokensByUser(user.getId());
        if(validToken.isEmpty())
        {
            return;
        }
        validToken.forEach(t->{
            t.setRevoked(true);
            t.setExpired(true);
        });
        tokenRepository.saveAll(validToken);
    }
}
