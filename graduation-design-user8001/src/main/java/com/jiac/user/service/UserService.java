package com.jiac.user.service;

import com.jiac.common.dto.FollowUserDto;
import com.jiac.common.dto.UserDto;
import com.jiac.common.vo.PageVo;
import com.jiac.user.request.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * FileName: UserService
 * Author: Jiac
 * Date: 2022/2/2 14:16
 */
public interface UserService {
    UserDto login(UserLoginRequest request);

    UserDto register(UserRegisterRequest request);

    UserDto uploadAvatar(String username, MultipartFile file) throws IOException;

    UserDto modifyMessage(UserModifyMessageRequest request);

    Boolean userExist(String username);

    Boolean follow(String username, String followUsername);

    Boolean unfollow(String username, String followUsername);

    Integer countFollow(String username);

    Integer countFollowed(String followUsername);

    PageVo<FollowUserDto> getFollowList(GetFollowListRequest request);

    UserDto modifyPassword(ModifyPasswordRequest request);

    UserDto findPassword(FindPasswordRequest request);

    Boolean getUserFollow(String username, String articleAuthor);

    UserDto getUserByUsername(String username);

    List<UserDto> searchUser(String keyword, String username);
}
