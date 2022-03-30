package com.jiac.chat.feign;

import com.jiac.common.utils.CommonType;
import com.jiac.common.vo.UserVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * FileName: UserFeign
 * Author: Jiac
 * Date: 2022/2/9 12:45
 */
@FeignClient(value = "graduation-design-user8001")
public interface UserFeign {

    @GetMapping("/user/getUserByUsername")
    CommonType<UserVo> getUserByUsername(@RequestParam("username") String username);

}
