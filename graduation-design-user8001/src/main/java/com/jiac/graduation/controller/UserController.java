package com.jiac.graduation.controller;

import com.jiac.common.utils.CommonType;
import com.jiac.common.utils.ErrorEnum;
import com.jiac.common.utils.MyException;
import com.jiac.graduation.dto.UserDto;
import com.jiac.graduation.request.UserLoginRequest;
import com.jiac.graduation.request.UserRegisterRequest;
import com.jiac.graduation.service.UserService;
import com.jiac.graduation.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * FileName: UserController
 * Author: Jiac
 * Date: 2022/2/2 13:33
 */
@RestController
@RequestMapping(value = "/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @PostMapping("/login")
    public CommonType<UserVo> login(String username, String password) throws MyException {
        UserLoginRequest request = UserLoginRequest.of(username, password);
        UserDto userDto = userService.login(request);
        return CommonType.success(UserVo.of(userDto), "登录成功");
    }

    @ResponseBody
    @PostMapping("/register")
    public CommonType<UserVo> register(String username, String password, String confirmPwd) {
        UserRegisterRequest request = UserRegisterRequest.of(username, password, confirmPwd);
        UserDto userDto = userService.register(request);
        return CommonType.success(UserVo.of(userDto), "注册成功");
    }
}
