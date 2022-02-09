package com.jiac.graduation.service;

import com.jiac.graduation.dto.UserCookieDto;
import com.jiac.graduation.dto.UserDto;

/**
 * FileName: UserCookieService
 * Author: Jiac
 * Date: 2022/2/7 16:32
 */
public interface UserCookieService {
    UserCookieDto addUserCookie(String cookie, String username, long expireTimestamp);

    UserCookieDto getUserByCookie(String cookie);

    void deleteUserCookie(String username);
}
