package com.jiac.common.interceptor;

import com.jiac.common.utils.ErrorEnum;
import com.jiac.common.utils.MyException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * FileName: LoginInterceptor
 * Author: Jiac
 * Date: 2022/2/10 12:44
 */
public class LoginInterceptor implements HandlerInterceptor {

    private final String COOKIE_NAME = "userCookie";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        if(cookies == null || cookies.length == 0) {
            throw new MyException(ErrorEnum.USER_MESSAGE_EXPIRE);
        }
        for(Cookie cookie : cookies) {
            if(cookie.getName().equals(COOKIE_NAME)) {
                // 如果有userCookie的cookie 就放行
                return true;
            }
        }
        throw new MyException(ErrorEnum.USER_MESSAGE_EXPIRE);
    }
}
