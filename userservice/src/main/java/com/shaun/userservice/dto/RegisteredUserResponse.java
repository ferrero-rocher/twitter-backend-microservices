package com.shaun.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisteredUserResponse {
    private String jwt;
}
