package com.jiac.graduation.dto;

import com.jiac.common.entity.User;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * FileName: UserDto
 * Author: Jiac
 * Date: 2022/2/2 15:15
 */
@Data
public class UserDto {
    private String username;

    private String password;

    private String nickname;

    private String school;

    private String college;

    private String specialty;

    private String gender;

    private String resume;

    private String profile;

    public static UserDto of(User user) {
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        return userDto;
    }
}
