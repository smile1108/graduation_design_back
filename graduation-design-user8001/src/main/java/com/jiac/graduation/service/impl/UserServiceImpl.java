package com.jiac.graduation.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.jiac.common.utils.ErrorEnum;
import com.jiac.common.utils.MyException;
import com.jiac.graduation.dto.UserDto;
import com.jiac.graduation.entity.User;
import com.jiac.graduation.repository.UserRepository;
import com.jiac.graduation.request.UserLoginRequest;
import com.jiac.graduation.request.UserRegisterRequest;
import com.jiac.graduation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * FileName: UserServiceImpl
 * Author: Jiac
 * Date: 2022/2/2 14:17
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDto login(UserLoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername());
        if(user == null) {
            throw new MyException(ErrorEnum.USER_NOT_EXIST);
        }
        if(!user.getPassword().equals(request.getPassword())) {
            throw new MyException(ErrorEnum.PASSWORD_WRONG);
        }
        return UserDto.of(user);
    }

    @Override
    public UserDto register(UserRegisterRequest request) {
        User user = userRepository.findByUsername(request.getUsername());
        if(user != null) {
            throw new MyException(ErrorEnum.USER_IS_EXIST);
        }
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        RandomUtil randomUtil = new RandomUtil();
        user.setNickname(randomUtil.randomString(20));
        userRepository.save(user);

        return UserDto.of(user);
    }
}
