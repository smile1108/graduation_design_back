package com.jiac.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * FileName: WebConfig
 * Author: Jiac
 * Date: 2022/2/7 11:35
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")//设置允许跨域的路径
                //注意需要给ip，而不是域名，之前写的是localhost，搞两天才发现不能使用localhost，需要用ip，否则死也读取不到。。。。。。
                .allowedOrigins("http://localhost:8080")//允许指定前端的请求跨域，传cookie不能*
                .allowedHeaders("*")//允许的请求头
                .allowedMethods("*")//允许请求的方法，get、post、put....
                .allowCredentials(true)//使用凭证
                .maxAge(3600);//允许跨域时间
    }
}
