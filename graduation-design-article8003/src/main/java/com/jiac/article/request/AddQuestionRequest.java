package com.jiac.article.request;

import lombok.Data;

/**
 * FileName: AddQuestionRequest
 * Author: Jiac
 * Date: 2022/3/13 11:20
 */
@Data
public class AddQuestionRequest {
    private String title;
    private String content;
    private String username;

    public static AddQuestionRequest of(String title, String content, String username) {
        AddQuestionRequest request = new AddQuestionRequest();
        request.setTitle(title);
        request.setContent(content);
        request.setUsername(username);
        return request;
    }
}
