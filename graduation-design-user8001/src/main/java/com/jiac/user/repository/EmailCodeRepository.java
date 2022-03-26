package com.jiac.user.repository;

import com.jiac.common.entity.EmailCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * FileName: EmailCodeRepository
 * Author: Jiac
 * Date: 2022/3/26 9:52
 */
@Repository
public interface EmailCodeRepository extends JpaRepository<EmailCode, String> {

    @Query(value = "select * from email_code where email = ?1", nativeQuery = true)
    EmailCode findEmailCodeByEmail(String email);
}
