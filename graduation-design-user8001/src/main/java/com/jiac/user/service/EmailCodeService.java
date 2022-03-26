package com.jiac.user.service;

/**
 * FileName: EmailCodeService
 * Author: Jiac
 * Date: 2022/3/26 9:50
 */
public interface EmailCodeService {
    void updateCode(String email, String code);

    void validateCode(String email, String code);
}
