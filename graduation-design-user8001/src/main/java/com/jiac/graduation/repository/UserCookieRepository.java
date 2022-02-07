package com.jiac.graduation.repository;

import com.jiac.graduation.entity.UserCookie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * FileName: UserCookieRepository
 * Author: Jiac
 * Date: 2022/2/7 16:34
 */
@Repository
public interface UserCookieRepository extends JpaRepository<UserCookie, String> {
}
