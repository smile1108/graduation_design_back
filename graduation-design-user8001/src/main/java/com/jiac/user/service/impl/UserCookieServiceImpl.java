package com.jiac.user.service.impl;

import com.jiac.common.entity.User;
import com.jiac.common.entity.UserCookie;
import com.jiac.common.utils.ErrorEnum;
import com.jiac.common.utils.MyException;
import com.jiac.common.dto.UserCookieDto;
import com.jiac.user.repository.UserCookieRepository;
import com.jiac.user.service.UserCookieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * FileName: UserCookieServiceImpl
 * Author: Jiac
 * Date: 2022/2/7 16:32
 */
@Service
public class UserCookieServiceImpl implements UserCookieService {

    @Autowired
    private UserCookieRepository userCookieRepository;

    @Override
    public UserCookieDto addUserCookie(String cookie, String username, long expireTimestamp) {
        UserCookie userCookie = new UserCookie();
        userCookie.setCookie(cookie);
        userCookie.setExpireTimestamp(expireTimestamp);
        User user = new User();
        user.setUsername(username);
        userCookie.setUser(user);
        UserCookie saveUserCookie = userCookieRepository.save(userCookie);
        return UserCookieDto.of(saveUserCookie);
    }

    @Override
    public UserCookieDto getUserByCookie(String cookie) {
        UserCookie userCookie = userCookieRepository.findByCookie(cookie);
        if(userCookie == null) {
            throw new MyException(ErrorEnum.ILLEGAL_COOKIE);
        }
        return UserCookieDto.of(userCookie);
    }

    @Override
    public void deleteUserCookie(String username) {
        userCookieRepository.deleteByUser(username);
    }
}
