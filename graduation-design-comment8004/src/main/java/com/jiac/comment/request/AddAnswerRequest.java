package com.jiac.comment.request;

import lombok.Data;

/**
 * FileName: AddAnswerRequest
 * Author: Jiac
 * Date: 2022/3/16 9:47
 */
@Data
public class AddAnswerRequest {
    private String content;
    private String username;
    private String questionId;

    public static AddAnswerRequest of(String content, String username, String questionId) {
        AddAnswerRequest request = new AddAnswerRequest();
        request.setContent(content);
        request.setUsername(username);
        request.setQuestionId(questionId);
        return request;
    }
}
