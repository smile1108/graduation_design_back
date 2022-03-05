package com.jiac.user.repository;

import com.jiac.common.entity.UserFollow;
import com.jiac.common.entity.UserFollowKey;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * FileName: UserFollowRepository
 * Author: Jiac
 * Date: 2022/3/5 10:26
 */
public interface UserFollowRepository extends JpaRepository<UserFollow, UserFollowKey> {
}
