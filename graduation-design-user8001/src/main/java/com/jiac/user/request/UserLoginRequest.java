package com.jiac.user.request;

import com.jiac.common.utils.ErrorEnum;
import com.jiac.common.utils.MyException;
import lombok.Data;

/**
 * FileName: UserLoginRequest
 * Author: Jiac
 * Date: 2022/2/2 15:12
 */
@Data
public class UserLoginRequest {
    private String username;
    private String password;

    public static UserLoginRequest of(String username, String password) throws MyException {
        // 密码验证 正则表达式
        String pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&\\\\.])[A-Za-z\\d@$!%*?&\\\\.]{8,20}$";
        if(username == null || "".equals(username)) {
            throw new MyException(ErrorEnum.USERNAME_EMPTY);
        }
        if(password == null || "".equals(password)) {
            throw new MyException(ErrorEnum.PASSWORD_EMPTY);
        }
        if(username.length() > 20 || username.length() < 8) {
            throw new MyException(ErrorEnum.USERNAME_LENGTH_NOT_FIT);
        }
        if(!password.matches(pattern)) {
            // 密码格式不正确
            throw new MyException(ErrorEnum.PASSWORD_PATTERN_WRONG);
        }
        UserLoginRequest request = new UserLoginRequest();
        request.setUsername(username);
        request.setPassword(password);
        return request;
    }
}
