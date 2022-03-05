package com.jiac.user.controller;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.jiac.common.utils.CommonType;
import com.jiac.common.utils.ErrorEnum;
import com.jiac.common.utils.MyException;
import com.jiac.common.dto.UserCookieDto;
import com.jiac.common.dto.UserDto;
import com.jiac.user.request.UserLoginRequest;
import com.jiac.user.request.UserModifyMessageRequest;
import com.jiac.user.request.UserRegisterRequest;
import com.jiac.user.service.UserCookieService;
import com.jiac.user.service.UserService;
import com.jiac.common.vo.UserCookieVo;
import com.jiac.common.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

    @Value("${cookie.expires}")
    private Integer expires;
    @Value("${cookie.name}")
    private String cookieName;

    @ResponseBody
    @PostMapping("/login")
    public CommonType<UserCookieVo> login(@RequestParam("username") String username, @RequestParam("password") String password,
                                          HttpServletRequest request, HttpServletResponse response) throws MyException {
        UserLoginRequest logInRequest = UserLoginRequest.of(username, password);
        UserDto userDto = userService.login(logInRequest);
        // 登录成功之后 响应中添加cookie 表示用户登录的信息
        String userCookieStr = RandomUtil.randomString(32);
        Cookie cookie = new Cookie(cookieName, userCookieStr);
        cookie.setMaxAge(expires);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        // 获取cookie过期时间
        DateTime now = DateUtil.date();
        // cookie过期时间为5分钟 要和响应给前端的过期时间一致
        DateTime offset = now.offset(DateField.SECOND, expires);
        long expireTimestamp = offset.toTimestamp().getTime();

        // 然后将用户的cookie存入数据库中
        UserCookieDto userCookieDto = userCookieService.addUserCookie(userCookieStr, username, expireTimestamp);

        return CommonType.success(UserCookieVo.of(userCookieDto), "登录成功");
    }

    @ResponseBody
    @GetMapping("/logout")
    public CommonType<UserVo> logout(@RequestParam("username") String username,
                                     HttpServletResponse response) {
        userCookieService.deleteUserCookie(username);
        // 然后删除cookie
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return CommonType.success(null, "退出登录成功");
    }

    @ResponseBody
    @GetMapping("/autoLogin")
    public CommonType<UserCookieVo> autoLogin(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for(Cookie c : cookies) {
                if(cookieName.equals(c.getName())) {
                    // 如果存在键为userCookie的cookie 表示当前可以直接获取到用户信息 cookie还没有过期
                    UserCookieDto userCookieDto = userCookieService.getUserByCookie(c.getValue());
                    return CommonType.success(UserCookieVo.of(userCookieDto), "自动登录成功");
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

    @ResponseBody
    @GetMapping("/follow")
    public CommonType<Boolean> follow(@RequestParam("username") String username,
                                      @RequestParam("followUsername") String followUsername) {
        Boolean follow = userService.follow(username, followUsername);
        return CommonType.success(follow, "关注成功");
    }

    @ResponseBody
    @GetMapping("/unfollow")
    private CommonType<Boolean> unfollow(@RequestParam("username") String username,
                                         @RequestParam("followUsername") String followUsername) {
        Boolean unfollow = userService.unfollow(username, followUsername);
        return CommonType.success(unfollow, "取消关注成功");
    }

    @ResponseBody
    @GetMapping("/userExist")
    public CommonType<Boolean> userExist(@RequestParam("username") String username) {
        Boolean exist = userService.userExist(username);
        if(exist) {
            return CommonType.success(exist, "用户存在");
        }
        return CommonType.fail(ErrorEnum.USER_NOT_EXIST);
    }
}
