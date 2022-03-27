package com.jiac.user.request;

import com.jiac.common.utils.ErrorEnum;
import com.jiac.common.utils.MyException;
import lombok.Data;

/**
 * FileName: ModifyPasswordRequest
 * Author: Jiac
 * Date: 2022/3/27 9:31
 */
@Data
public class ModifyPasswordRequest {
    private String username;
    private String oldPassword;
    private String newPassword;
    private String confirmNewPassword;

    public static ModifyPasswordRequest of(String username, String oldPassword, String newPassword,
                                           String confirmNewPassword) {
        String pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&\\\\.])[A-Za-z\\d@$!%*?&\\\\.]{8,20}$";
        if(oldPassword == null || "".equals(oldPassword) || newPassword == null || "".equals(newPassword) || confirmNewPassword == null || "".equals(confirmNewPassword)) {
            throw new MyException(ErrorEnum.PASSWORD_EMPTY);
        }
        if(!newPassword.equals(confirmNewPassword)) {
            throw new MyException(ErrorEnum.PASSWORD_NOT_FIT);
        }
        if(!newPassword.matches(pattern)) {
            throw new MyException(ErrorEnum.PASSWORD_PATTERN_WRONG);
        }
        ModifyPasswordRequest request = new ModifyPasswordRequest();
        request.setUsername(username);
        request.setOldPassword(oldPassword);
        request.setNewPassword(newPassword);
        request.setConfirmNewPassword(confirmNewPassword);
        return request;
    }
}
