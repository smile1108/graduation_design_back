package com.jiac.graduation.request;

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
        if(username == null || "".equals(username)) {
            throw new MyException(ErrorEnum.USERNAME_EMPTY);
        }
        if(password == null || "".equals(password)) {
            throw new MyException(ErrorEnum.PASSWORD_EMPTY);
        }
        UserLoginRequest request = new UserLoginRequest();
        request.setUsername(username);
        request.setPassword(password);
        return request;
    }
}
