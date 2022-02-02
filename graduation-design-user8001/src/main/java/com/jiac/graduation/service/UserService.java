package com.jiac.graduation.service;

import com.jiac.graduation.dto.UserDto;
import com.jiac.graduation.request.UserLoginRequest;

/**
 * FileName: UserService
 * Author: Jiac
 * Date: 2022/2/2 14:16
 */
public interface UserService {
    UserDto login(UserLoginRequest request);
}
