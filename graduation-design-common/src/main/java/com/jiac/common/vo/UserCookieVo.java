package com.jiac.common.vo;

import com.jiac.common.dto.UserCookieDto;
import lombok.Data;

/**
 * FileName: UserCookieVo
 * Author: Jiac
 * Date: 2022/2/9 10:34
 */
@Data
public class UserCookieVo {
    private UserVo userVo;

    private Long expireTimestamp;

    public static UserCookieVo of(UserCookieDto userCookieDto) {
        UserCookieVo userCookieVo = new UserCookieVo();
        userCookieVo.setExpireTimestamp(userCookieDto.getExpireTimestamp());
        userCookieVo.setUserVo(UserVo.of(userCookieDto.getUserDto()));
        return userCookieVo;
    }
}
