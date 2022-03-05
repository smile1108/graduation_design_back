package com.jiac.user.service;

import com.jiac.common.dto.UserDto;
import com.jiac.user.request.UserLoginRequest;
import com.jiac.user.request.UserModifyMessageRequest;
import com.jiac.user.request.UserRegisterRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

    Boolean getUserFollow(String username, String articleAuthor);
}
