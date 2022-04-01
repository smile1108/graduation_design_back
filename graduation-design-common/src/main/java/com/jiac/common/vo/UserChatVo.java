package com.jiac.common.vo;

import com.jiac.common.dto.UserDto;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * FileName: UserVo
 * Author: Jiac
 * Date: 2022/2/2 14:06
 */
@Data
public class UserChatVo {

    private String username;

    private String email;

    private String nickname;

    private String school;

    private String college;

    private String specialty;

    private String gender;

    private String resume;

    private String profile;

    private Integer unreadCount;

    public static UserChatVo of(UserVo userVo) {
        UserChatVo userChatVo = new UserChatVo();
        BeanUtils.copyProperties(userVo, userChatVo);
        return userChatVo;
    }
}
