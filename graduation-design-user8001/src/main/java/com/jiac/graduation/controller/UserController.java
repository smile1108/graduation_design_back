package com.jiac.graduation.controller;

import cn.hutool.core.util.RandomUtil;
import com.jiac.common.utils.CommonType;
import com.jiac.common.utils.ErrorEnum;
import com.jiac.common.utils.MyException;
import com.jiac.graduation.dto.UserDto;
import com.jiac.graduation.request.UserLoginRequest;
import com.jiac.graduation.request.UserModifyMessageRequest;
import com.jiac.graduation.request.UserRegisterRequest;
import com.jiac.graduation.service.UserCookieService;
import com.jiac.graduation.service.UserService;
import com.jiac.graduation.vo.UserVo;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;

/**
 * FileName: UserController
 * Author: Jiac
 * Date: 2022/2/2 13:33
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserCookieService userCookieService;

    @ResponseBody
    @PostMapping("/login")
    public CommonType<UserVo> login(@RequestParam("username") String username, @RequestParam("password") String password,
                                    HttpServletRequest request, HttpServletResponse response) throws MyException {
        UserLoginRequest logInRequest = UserLoginRequest.of(username, password);
        UserDto userDto = userService.login(logInRequest);
        // 登录成功之后 响应中添加cookie 表示用户登录的信息
        String userCookieStr = RandomUtil.randomString(32);
        Cookie cookie = new Cookie("userCookie", userCookieStr);
        cookie.setMaxAge(300);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        // 然后将用户的cookie存入数据库中
        userCookieService.addUserCookie(userCookieStr, username);

        return CommonType.success(UserVo.of(userDto), "登录成功");
    }

    @ResponseBody
    @GetMapping("/autoLogin")
    public CommonType<UserVo> autoLogin(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for(Cookie c : cookies) {
                if("userCookie".equals(c.getName())) {
                    // 如果存在键为userCookie的cookie 表示当前可以直接获取到用户信息 cookie还没有过期
                    UserDto userDto = userCookieService.getUserByCookie(c.getValue());
                    return CommonType.success(UserVo.of(userDto), "自动登录成功");
                }
            }
        }
        return CommonType.success(null, "cookie已失效, 请重新登录");
    }

    @ResponseBody
    @PostMapping("/register")
    public CommonType<UserVo> register(String username, String password, String confirmPwd) {
        UserRegisterRequest request = UserRegisterRequest.of(username, password, confirmPwd);
        UserDto userDto = userService.register(request);
        return CommonType.success(UserVo.of(userDto), "注册成功");
    }

    @ResponseBody
    @PostMapping("/uploadAvatar")
    // 用户上传头像的接口
    public CommonType<UserVo> uploadAvatar(@RequestParam("username") String username,
                                           @RequestParam("file") MultipartFile file) throws IOException {
        UserDto userDto = userService.uploadAvatar(username, file);
        return CommonType.success(UserVo.of(userDto), "上传头像成功");
    }

    @ResponseBody
    @PostMapping("/modifyMessage")
    public CommonType<UserVo> modifyMessage(@RequestParam("username") String username,
                                            @RequestParam("nickname") String nickname,
                                            @RequestParam("school") String school,
                                            @RequestParam("college") String college,
                                            @RequestParam("specialty") String specialty,
                                            @RequestParam("gender") String gender,
                                            @RequestParam("resume") String resume) {
        UserModifyMessageRequest request = UserModifyMessageRequest.of(username, nickname, school, college, specialty, gender, resume);
        UserDto userDto = userService.modifyMessage(request);
        return CommonType.success(UserVo.of(userDto), "修改信息成功");
    }
}
