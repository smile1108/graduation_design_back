package com.jiac.backlog.request;

import com.jiac.common.utils.ErrorEnum;
import com.jiac.common.utils.MyException;
import lombok.Data;

/**
 * FileName: BacklogDoneRequest
 * Author: Jiac
 * Date: 2022/2/9 13:38
 */
@Data
public class BacklogDoneRequest {
    private String id;
    private String username;

    public static BacklogDoneRequest of(String id, String username) {
        // 先检验字段是否符合标准
        if(id.length() > 10) {
            throw new MyException(ErrorEnum.ID_TOO_LENGTH);
        }
        if(username.length() < 8 || username.length() > 20) {
            throw new MyException(ErrorEnum.USERNAME_LENGTH_NOT_FIT);
        }
        BacklogDoneRequest request = new BacklogDoneRequest();
        request.setId(id);
        request.setUsername(username);
        return request;
    }
}
