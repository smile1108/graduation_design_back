package com.jiac.user.vo;

import com.jiac.user.dto.UserDto;
import lombok.Data;
import org.springframework.beans.BeanUtils;

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

    private static final String PROFILE_URL = "http://localhost/images/";

    public static UserVo of(UserDto userDto) {
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(userDto, userVo);
        userVo.setProfile(PROFILE_URL + userDto.getProfile());
        return userVo;
    }
}
