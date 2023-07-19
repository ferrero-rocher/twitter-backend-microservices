package com.shaun.followservice.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.Date;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="user_relation")
public class UserRelation {

    @EmbeddedId
    private UserRelationPk userRelationPk;

    private Date createdAt=calculatime();


    public UserRelation(UserRelationPk userRelationPk){
        this.userRelationPk=userRelationPk;
    }

    public UserRelation(Integer userId){
        this.userRelationPk.getUser1();
    }







    private Date calculatime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        return new Date(calendar.getTime().getTime());
    }
}
