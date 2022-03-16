package com.jiac.comment.request;

import lombok.Data;

/**
 * FileName: DeleteAnswerRequest
 * Author: Jiac
 * Date: 2022/3/16 10:04
 */
@Data
public class DeleteAnswerRequest {
    private String id;
    private String username;

    public static DeleteAnswerRequest of(String id, String username) {
        DeleteAnswerRequest request = new DeleteAnswerRequest();
        request.setId(id);
        request.setUsername(username);
        return request;
    }
}
