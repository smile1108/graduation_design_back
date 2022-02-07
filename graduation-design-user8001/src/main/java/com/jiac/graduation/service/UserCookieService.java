package com.jiac.graduation.service;

import com.jiac.graduation.dto.UserDto;

/**
 * FileName: UserCookieService
 * Author: Jiac
 * Date: 2022/2/7 16:32
 */
public interface UserCookieService {
    void addUserCookie(String cookie, String username);

    UserDto getUserByCookie(String cookie);

    void deleteUserCookie(String username);
}
