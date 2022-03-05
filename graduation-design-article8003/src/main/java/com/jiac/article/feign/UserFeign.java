package com.jiac.article.feign;

import com.jiac.common.utils.CommonType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * FileName: UserFeign
 * Author: Jiac
 * Date: 2022/2/9 12:45
 */
@FeignClient(value = "graduation-design-user8001")
public interface UserFeign {

    @GetMapping(value = "/user/userExist")
    CommonType<Boolean> userExist(@RequestParam("username") String username);

    @GetMapping(value = "/user/getUserFollow")
    CommonType<Boolean> getUserFollow(@RequestParam("username") String username,
                                      @RequestParam("articleAuthor") String articleAuthor);
}
