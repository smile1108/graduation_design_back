package com.jiac.backlog.request;

import com.jiac.common.utils.ErrorEnum;
import com.jiac.common.utils.MyException;
import lombok.Data;

/**
 * FileName: BacklogCheckAllOrNoneRequest
 * Author: Jiac
 * Date: 2022/2/10 15:18
 */
@Data
public class BacklogCheckAllOrNoneRequest {
    private Boolean done;
    private String username;

    public static BacklogCheckAllOrNoneRequest of(Boolean done, String username) {
        if(username.length() < 8 || username.length() > 20) {
            throw new MyException(ErrorEnum.USERNAME_LENGTH_NOT_FIT);
        }
        BacklogCheckAllOrNoneRequest request = new BacklogCheckAllOrNoneRequest();
        request.setDone(done);
        request.setUsername(username);
        return request;
    }
}
