package com.jiac.user.service;

import com.jiac.user.dto.UserCookieDto;

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
