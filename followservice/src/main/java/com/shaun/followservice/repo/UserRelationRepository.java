package com.shaun.followservice.repo;

import com.shaun.followservice.entity.UserRelation;
import com.shaun.followservice.entity.UserRelationPk;
import com.shaun.followservice.entity.UserRelation;
import com.shaun.followservice.entity.UserRelationPk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRelationRepository extends JpaRepository<UserRelation, UserRelationPk> {

    /*@Query("SELECT ur FROM UserRelation ur WHERE ur.pk.user2.userId = :followedId")
    List<UserRelation> findUserRelationByFollowedId(Integer followedId);*/

   // List<UserRelation> findByFollowedId(Integer followedId);
     List<UserRelation> findByUserRelationPkUser2Id(Integer followedId);
    List<UserRelation> findByUserRelationPkUser1Id(Integer followedId);
}
