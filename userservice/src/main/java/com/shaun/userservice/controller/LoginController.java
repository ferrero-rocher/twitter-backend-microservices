package com.shaun.userservice.controller;

import com.shaun.userservice.dto.LoginRequest;
import com.shaun.userservice.dto.LoginResponse;
import com.shaun.userservice.dto.RegisterUserRequest;
import com.shaun.userservice.dto.RegisteredUserResponse;
import com.shaun.userservice.repos.TokenRepository;
import com.shaun.userservice.services.LoginService;
import com.shaun.userservice.services.LoginServiceImpl;
import com.shaun.userservice.services.UserService;
import com.shaun.userservice.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@ControllerAdvice
@RequestMapping("api/auth")
public class LoginController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserService userService;



    @PostMapping("/login")
    public LoginResponse generateJwtToken(@RequestBody LoginRequest loginRequest, HttpServletResponse httpServletResponse) throws BadCredentialsException, DisabledException, UsernameNotFoundException, IOException
    {
        LoginResponse loginResponse;
        try
        {
            //authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));
             loginResponse =loginService.authenticate(loginRequest);
        }
        catch(BadCredentialsException ex)
        {
            throw new BadCredentialsException("incorrect username or password");
        }
        catch (DisabledException disabledException) {
            httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND, "User is not activated");
            return null;
        }



        return loginResponse;
    }

    @PostMapping("/logout")
    public void invalidateJwtToken( HttpServletRequest request) throws BadCredentialsException, DisabledException, UsernameNotFoundException, IOException
    {
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);
        loginService.invalidateToken(token);





    }

    @PostMapping("/register")
    public ResponseEntity<?> RegisterUser(@RequestBody RegisterUserRequest registerUserRequest)
    {
        RegisteredUserResponse registeredUserResponse =userService.createUser(registerUserRequest);

        return new ResponseEntity<>(registeredUserResponse, HttpStatus.CREATED);
    }

}
