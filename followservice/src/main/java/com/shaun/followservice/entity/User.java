package com.shaun.followservice.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    private String username;

    private String email;

    private String password;

    private String fullname;

    private String phone;

    private boolean isCelebrity=false;

    private Date createdAt=calculatime();

    private Date updatedAt;
    @OneToMany(mappedBy = "user")
    private List<Token> tokens;

    /*@OneToMany(mappedBy = "user")
    private List<Tweet> tweets;*/


    private Date calculatime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        return new Date(calendar.getTime().getTime());
    }

    public User(int id)
    {
        this.id=id;
    }


}

