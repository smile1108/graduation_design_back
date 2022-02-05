package com.jiac.graduation.service;

import com.jiac.graduation.dto.UserDto;
import com.jiac.graduation.request.UserLoginRequest;
import com.jiac.graduation.request.UserRegisterRequest;
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
}
