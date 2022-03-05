package com.jiac.user.repository;

import com.jiac.common.entity.UserFollow;
import com.jiac.common.entity.UserFollowKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * FileName: UserFollowRepository
 * Author: Jiac
 * Date: 2022/3/5 10:26
 */
public interface UserFollowRepository extends JpaRepository<UserFollow, UserFollowKey> {

    @Query(value = "select * from user_follow where username = ?1 and follow_username = ?2", nativeQuery = true)
    UserFollow getUserFollowByUsernameAndFollowUsername(String username, String followUsername);
}
