package com.jiac.article.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * FileName: FeignHeaderConfiguration
 * Author: Jiac
 * Date: 2022/2/10 13:24
 */
@Configuration
public class FeignHeaderConfiguration {
    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if(attrs != null) {
                HttpServletRequest request = attrs.getRequest();
                // 获取cookie中的信息
                Cookie[] cookies = request.getCookies();
                if(cookies != null && cookies.length > 0) {
                    for(Cookie cookie : cookies) {
                        requestTemplate.header(cookie.getName(), cookie.getValue());
                    }
                }
            }
        };
    }
}
