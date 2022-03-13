package com.jiac.article.request;

import lombok.Data;

/**
 * FileName: DeleteQuestionRequest
 * Author: Jiac
 * Date: 2022/3/13 12:26
 */
@Data
public class DeleteQuestionRequest {
    private String id;
    private String username;

    public static DeleteQuestionRequest of(String id, String username) {
        DeleteQuestionRequest request = new DeleteQuestionRequest();
        request.setId(id);
        request.setUsername(username);
        return request;
    }
}
