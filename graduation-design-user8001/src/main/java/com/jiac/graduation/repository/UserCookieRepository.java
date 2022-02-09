package com.jiac.graduation.repository;

import com.jiac.common.entity.UserCookie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * FileName: UserCookieRepository
 * Author: Jiac
 * Date: 2022/2/7 16:34
 */
@Repository
public interface UserCookieRepository extends JpaRepository<UserCookie, String> {

    UserCookie findByCookie(String cookie);

    @Transactional
    @Modifying
    @Query(value = "delete from user_cookie where username = ?1", nativeQuery = true)
    void deleteByUser(String username);
}
