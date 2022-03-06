package com.jiac.user.repository;

import com.jiac.common.entity.UserFollow;
import com.jiac.common.entity.UserFollowKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * FileName: UserFollowRepository
 * Author: Jiac
 * Date: 2022/3/5 10:26
 */
public interface UserFollowRepository extends JpaRepository<UserFollow, UserFollowKey>, JpaSpecificationExecutor {

    @Query(value = "select * from user_follow where username = ?1 and follow_username = ?2", nativeQuery = true)
    UserFollow getUserFollowByUsernameAndFollowUsername(String username, String followUsername);

    @Query(value = "select count(*) from user_follow where username = ?1", nativeQuery = true)
    Integer countFollow(String username);

    // 查询一个用户被多少人关注
    @Query(value = "select count(*) from user_follow where follow_username = ?1", nativeQuery = true)
    Integer countFollowed(String username);
}
