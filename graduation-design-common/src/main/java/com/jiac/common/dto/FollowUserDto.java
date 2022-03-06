package com.jiac.common.dto;

import com.jiac.common.entity.User;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * FileName: FollowUserDto
 * Author: Jiac
 * Date: 2022/3/6 10:27
 */
@Data
public class FollowUserDto {

    private String username;

    private String nickname;

    private String school;

    private String college;

    private String specialty;

    private String gender;

    private String resume;

    private String profile;

    private Integer followSum;

    private Integer articleSum;

    public static FollowUserDto of(User user) {
        FollowUserDto followUserDto = new FollowUserDto();
        BeanUtils.copyProperties(user, followUserDto);
        return followUserDto;
    }
}
