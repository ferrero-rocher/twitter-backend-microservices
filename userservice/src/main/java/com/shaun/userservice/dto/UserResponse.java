package com.shaun.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse implements Serializable {
    private String username;

    private String email;
    private String fullname;

    private String phone;
    private Date createdAt;

    private Boolean isCelebrity;
}
