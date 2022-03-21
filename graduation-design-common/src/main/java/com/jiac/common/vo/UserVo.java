package com.jiac.common.vo;

import com.jiac.common.dto.UserDto;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;

/**
 * FileName: UserVo
 * Author: Jiac
 * Date: 2022/2/2 14:06
 */
@Data
public class UserVo {

    private String username;

    private String nickname;

    private String school;

    private String college;

    private String specialty;

    private String gender;

    private String resume;

    private String profile;

    private static String PROFILE_URL = "http://localhost/images/";

    public static UserVo of(UserDto userDto) {
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(userDto, userVo);
        userVo.setProfile(PROFILE_URL + userDto.getProfile());
        return userVo;
    }
}
