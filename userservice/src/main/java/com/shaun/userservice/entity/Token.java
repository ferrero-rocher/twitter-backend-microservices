package com.shaun.userservice.entity;

import com.shaun.userservice.models.TokenType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="token")
public class Token {
    @Id
    @GeneratedValue
    private Integer id;

    private String token;
    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

    private boolean revoked;

    private boolean expired;

    @ManyToOne
    @JoinColumn(name = "userId",nullable = false)
    private User user;
}
