package com.jiac.backlog.request;

import com.jiac.common.utils.ErrorEnum;
import com.jiac.common.utils.MyException;
import lombok.Data;

/**
 * FileName: AddBacklogRequest
 * Author: Jiac
 * Date: 2022/2/9 12:04
 */
@Data
public class AddBacklogRequest {
    private String title;
    private String username;

    public static AddBacklogRequest of(String title, String username) {
        // 先检验字段是否符合标准
        if(title.length() > 20) {
            throw new MyException(ErrorEnum.BACKLOG_TITLE_TOO_LENGTH);
        }
        if(username.length() < 8 || username.length() > 20) {
            throw new MyException(ErrorEnum.USERNAME_LENGTH_NOT_FIT);
        }
        AddBacklogRequest request = new AddBacklogRequest();
        request.setTitle(title);
        request.setUsername(username);
        return request;
    }
}
