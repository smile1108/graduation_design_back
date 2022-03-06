package com.jiac.common.vo;

import com.jiac.common.dto.FollowUserDto;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * FileName: FollowUserVo
 * Author: Jiac
 * Date: 2022/3/6 10:13
 */
@Data
public class FollowUserVo {

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

    private static final String PROFILE_URL = "http://localhost/images/";

    public static FollowUserVo of(FollowUserDto followUserDto) {
        FollowUserVo followUserVo = new FollowUserVo();
        BeanUtils.copyProperties(followUserDto, followUserVo);
        followUserVo.setProfile(PROFILE_URL + followUserDto.getProfile());
        return followUserVo;
    }
}
