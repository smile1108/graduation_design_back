package com.jiac.graduation.request;

import com.jiac.common.utils.ErrorEnum;
import com.jiac.common.utils.MyException;
import lombok.Data;

/**
 * FileName: UserRegisterRequest
 * Author: Jiac
 * Date: 2022/2/2 16:59
 */
@Data
public class UserRegisterRequest {
    private String username;
    private String password;
    private String confirmPwd;

    public static UserRegisterRequest of(String username, String password, String confirmPwd) {
        if(username == null || "".equals(username)) {
            throw new MyException(ErrorEnum.USERNAME_EMPTY);
        }
        if(password == null || "".equals(password) || confirmPwd == null || "".equals(confirmPwd)) {
            throw new MyException(ErrorEnum.PASSWORD_EMPTY);
        }
        if(!password.equals(confirmPwd)) {
            throw new MyException(ErrorEnum.PASSWORD_NOT_FIT);
        }
        UserRegisterRequest request = new UserRegisterRequest();
        request.setUsername(username);
        request.setPassword(password);
        request.setConfirmPwd(confirmPwd);
        return request;
    }
}
