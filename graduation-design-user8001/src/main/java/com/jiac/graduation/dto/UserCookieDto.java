package com.jiac.graduation.dto;

import com.jiac.graduation.entity.User;
import com.jiac.graduation.entity.UserCookie;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * FileName: UserCookieDto
 * Author: Jiac
 * Date: 2022/2/9 10:34
 */
@Data
public class UserCookieDto {

    private String cookie;

    private UserDto userDto;

    private Long expireTimestamp;

    public static UserCookieDto of(UserCookie userCookie) {
        UserCookieDto userCookieDto = new UserCookieDto();
        BeanUtils.copyProperties(userCookie, userCookieDto);
        userCookieDto.setUserDto(UserDto.of(userCookie.getUser()));
        return userCookieDto;
    }
}
