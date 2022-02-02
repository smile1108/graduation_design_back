package com.jiac.graduation.service;

import com.jiac.graduation.dto.UserDto;
import com.jiac.graduation.request.UserLoginRequest;
import com.jiac.graduation.request.UserRegisterRequest;

/**
 * FileName: UserService
 * Author: Jiac
 * Date: 2022/2/2 14:16
 */
public interface UserService {
    UserDto login(UserLoginRequest request);

    UserDto register(UserRegisterRequest request);
}
