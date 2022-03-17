package com.jiac.user.config;

import com.jiac.common.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * FileName: LoginConfig
 * Author: Jiac
 * Date: 2022/2/10 12:48
 */
@Configuration
public class LoginConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(new LoginInterceptor());
        registration.addPathPatterns("/**"); // 所有路径都拦截
        registration.excludePathPatterns(  // 添加不拦截的路径
                "/user/login",
                "/user/autoLogin",
                "/user/register",
                "/user/countFollowed",
                "/user/userExist"
        );
    }
}
