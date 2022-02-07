package com.jiac.graduation.service.impl;

import com.jiac.common.utils.ErrorEnum;
import com.jiac.common.utils.MyException;
import com.jiac.graduation.dto.UserDto;
import com.jiac.graduation.entity.User;
import com.jiac.graduation.entity.UserCookie;
import com.jiac.graduation.repository.UserCookieRepository;
import com.jiac.graduation.service.UserCookieService;
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
    public void addUserCookie(String cookie, String username) {
        UserCookie userCookie = new UserCookie();
        userCookie.setCookie(cookie);
        User user = new User();
        user.setUsername(username);
        userCookie.setUser(user);
        userCookieRepository.save(userCookie);
    }

    @Override
    public UserDto getUserByCookie(String cookie) {
        UserCookie userCookie = userCookieRepository.findByCookie(cookie);
        if(userCookie == null) {
            throw new MyException(ErrorEnum.ILLEGAL_COOKIE);
        }
        return UserDto.of(userCookie.getUser());
    }

    @Override
    public void deleteUserCookie(String username) {
        userCookieRepository.deleteByUser(username);
    }
}
