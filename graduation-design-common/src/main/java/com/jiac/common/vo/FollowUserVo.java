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

    public static FollowUserVo of(FollowUserDto followUserDto) {
        FollowUserVo followUserVo = new FollowUserVo();
        BeanUtils.copyProperties(followUserDto, followUserVo);
        return followUserVo;
    }
}
