package com.jiac.graduation.vo;

import com.jiac.graduation.dto.UserDto;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * FileName: UserVo
 * Author: Jiac
 * Date: 2022/2/2 14:06
 */
@Data
public class UserVo {
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
