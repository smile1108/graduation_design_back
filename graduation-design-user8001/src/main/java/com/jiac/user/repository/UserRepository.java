package com.jiac.user.repository;

import com.jiac.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * FileName: UserRepository
 * Author: Jiac
 * Date: 2022/2/2 15:31
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {

    User findByUsername(String username);

}