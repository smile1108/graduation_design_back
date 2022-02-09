package com.jiac.user.dto;

import com.jiac.common.entity.UserCookie;
import lombok.Data;
import org.springframework.beans.BeanUtils;

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
