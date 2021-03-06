package com.jiac.user.controller;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.jiac.common.dto.FollowUserDto;
import com.jiac.common.utils.CommonType;
import com.jiac.common.utils.ErrorEnum;
import com.jiac.common.utils.MyException;
import com.jiac.common.dto.UserCookieDto;
import com.jiac.common.dto.UserDto;
import com.jiac.common.vo.FollowUserVo;
import com.jiac.common.vo.PageVo;
import com.jiac.user.request.*;
import com.jiac.user.service.EmailCodeService;
import com.jiac.user.service.EmailServer;
import com.jiac.user.service.UserCookieService;
import com.jiac.user.service.UserService;
import com.jiac.common.vo.UserCookieVo;
import com.jiac.common.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private EmailCodeService emailCodeService;

    @Autowired
    private EmailServer emailServer;

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
        // ?????????????????? ???????????????cookie ???????????????????????????
        String userCookieStr = RandomUtil.randomString(32);
        Cookie cookie = new Cookie(cookieName, userCookieStr);
        cookie.setMaxAge(expires);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        // ??????cookie????????????
        DateTime now = DateUtil.date();
        // ??????cookie???????????? expires?????????????????????????????????????????? ????????????
        DateTime offset = now.offset(DateField.SECOND, expires);
        long expireTimestamp = offset.toTimestamp().getTime();

        // ??????????????????cookie??????????????????
        UserCookieDto userCookieDto = userCookieService.addUserCookie(userCookieStr, username, expireTimestamp);

        return CommonType.success(UserCookieVo.of(userCookieDto), "????????????");
    }

    @ResponseBody
    @GetMapping("/logout")
    public CommonType<UserVo> logout(@RequestParam("username") String username,
                                     HttpServletResponse response) {
        userCookieService.deleteUserCookie(username);
        // ????????????cookie
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return CommonType.success(null, "??????????????????");
    }

    @ResponseBody
    @GetMapping("/autoLogin")
    public CommonType<UserCookieVo> autoLogin(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for(Cookie c : cookies) {
                if(cookieName.equals(c.getName())) {
                    // ??????????????????userCookie???cookie ?????????????????????????????????????????????
                    UserCookieDto userCookieDto = userCookieService.getUserByCookie(c.getValue());
                    long nowTimestamp = DateUtil.date().toTimestamp().getTime();
                    // ????????????????????????cookie????????????????????????cookie????????????
                    if(nowTimestamp < userCookieDto.getExpireTimestamp()) {
                        return CommonType.success(UserCookieVo.of(userCookieDto), "??????????????????");
                    }
                }
            }
        }
        return CommonType.success(null, "cookie?????????, ???????????????");
    }

    @ResponseBody
    @PostMapping("/register")
    public CommonType<UserVo> register(String username, String password, String confirmPwd,
                                       String email, String code) {
        UserRegisterRequest request = UserRegisterRequest.of(username, password, confirmPwd, email);
        emailCodeService.validateCode(email, code);
        UserDto userDto = userService.register(request);
        return CommonType.success(UserVo.of(userDto), "????????????");
    }

    @ResponseBody
    @PostMapping("/uploadAvatar")
    // ???????????????????????????
    public CommonType<UserVo> uploadAvatar(@RequestParam("username") String username,
                                           @RequestParam("file") MultipartFile file) throws IOException {
        UserDto userDto = userService.uploadAvatar(username, file);
        return CommonType.success(UserVo.of(userDto), "??????????????????");
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
        return CommonType.success(UserVo.of(userDto), "??????????????????");
    }

    @ResponseBody
    @GetMapping("/follow")
    public CommonType<Boolean> follow(@RequestParam("username") String username,
                                      @RequestParam("followUsername") String followUsername) {
        Boolean follow = userService.follow(username, followUsername);
        return CommonType.success(follow, "????????????");
    }

    @ResponseBody
    @GetMapping("/unfollow")
    private CommonType<Boolean> unfollow(@RequestParam("username") String username,
                                         @RequestParam("followUsername") String followUsername) {
        Boolean unfollow = userService.unfollow(username, followUsername);
        return CommonType.success(unfollow, "??????????????????");
    }

    @ResponseBody
    @GetMapping("/countFollow")
    private CommonType<Integer> countFollow(@RequestParam("username") String username) {
        return CommonType.success(userService.countFollow(username), "????????????");
    }

    @ResponseBody
    @GetMapping("/countFollowed")
    private CommonType<Integer> countFollowed(@RequestParam("followUsername") String followUsername) {
        return CommonType.success(userService.countFollowed(followUsername), "????????????");
    }

    @ResponseBody
    @GetMapping("/getFollowList")
    private CommonType<PageVo<FollowUserVo>> getFollowList(@RequestParam("username") String username,
                                                           @RequestParam(value = "page", required = false) Integer page,
                                                           @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        GetFollowListRequest request = GetFollowListRequest.of(username, page, pageSize);
        PageVo<FollowUserDto> followUserDtoPageVo = userService.getFollowList(request);
        return CommonType.success(transferFollowUserDtoPage2FollowUserVoPage(followUserDtoPageVo), "????????????");
    }

    @ResponseBody
    @GetMapping("/getCode")
    private CommonType<Boolean> getCode(@RequestParam("userEmail") String email) {
        String subject = "???????????????????????????????????????";
        String code = RandomUtil.randomString("0123456789", 4);
        String text = "????????????????????????: " + code + ", ????????????????????????3??????";
        emailServer.sendEmail(subject, email, text);
        emailCodeService.updateCode(email, code);
        return CommonType.success(true, "?????????????????????");
    }

    @ResponseBody
    @PostMapping("/modifyPassword")
    private CommonType<UserVo> modifyPassword(@RequestParam("username") String username,
                                              @RequestParam("oldPassword") String oldPassword,
                                              @RequestParam("newPassword") String newPassword,
                                              @RequestParam("confirmNewPassword") String confirmNewPassword) {
        ModifyPasswordRequest request = ModifyPasswordRequest.of(username, oldPassword, newPassword, confirmNewPassword);
        UserDto userDto = userService.modifyPassword(request);
        return CommonType.success(UserVo.of(userDto), "??????????????????");
    }

    @ResponseBody
    @PostMapping("/findPassword")
    private CommonType<Boolean> findPassword(@RequestParam("username") String username,
                                             @RequestParam("email") String email,
                                             @RequestParam("code") String code) {
        FindPasswordRequest request = FindPasswordRequest.of(username, email, code);
        emailCodeService.validateCode(email, code);
        UserDto userDto = userService.findPassword(request);
        String subject = "????????????????????????????????????";
        String text = "??????????????? " + userDto.getUsername() + "??????????????????: " + userDto.getPassword() + ", ??????????????????????????????, ??????????????????????????????????????????";
        emailServer.sendEmail(subject, email, text);
        return CommonType.success(true, "????????????");
    }

    @ResponseBody
    @GetMapping("/getUserFollow")
    private CommonType<Boolean> getUserFollow(@RequestParam("username") String username,
                                              @RequestParam("articleAuthor") String articleAuthor) {
        Boolean userFollow = userService.getUserFollow(username, articleAuthor);
        return CommonType.success(userFollow, "????????????");
    }

    @ResponseBody
    @GetMapping("/userExist")
    public CommonType<Boolean> userExist(@RequestParam("username") String username) {
        return CommonType.success(userService.userExist(username), "????????????");
    }

    @ResponseBody
    @GetMapping("/getUserByUsername")
    public CommonType<UserVo> getUserByUsername(@RequestParam("username") String username) {
        UserDto userDto = userService.getUserByUsername(username);
        return CommonType.success(UserVo.of(userDto), "????????????");
    }

    @ResponseBody
    @GetMapping("/searchUser")
    public CommonType<List<UserVo>> searchUser(@RequestParam("keyword") String keyword,
                                               @RequestParam("username") String username) {
        if(keyword == null || "".equals(keyword)) {
            return CommonType.success(new ArrayList<>(), "????????????");
        }
        return CommonType.success(userService.searchUser(keyword, username).stream().map(UserVo::of).collect(Collectors.toList()), "????????????");
    }

    private PageVo<FollowUserVo> transferFollowUserDtoPage2FollowUserVoPage(PageVo<FollowUserDto> followUserDtoPageVo) {
        PageVo<FollowUserVo> followUserVoPageVo = new PageVo<>();
        BeanUtils.copyProperties(followUserDtoPageVo, followUserVoPageVo);
        followUserVoPageVo.setLists(followUserDtoPageVo.getLists().stream().map(FollowUserVo::of).collect(Collectors.toList()));
        return followUserVoPageVo;
    }
}
