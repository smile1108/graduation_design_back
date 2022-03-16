package com.jiac.comment.request;

import lombok.Data;

/**
 * FileName: GetAnswerListRequest
 * Author: Jiac
 * Date: 2022/3/16 10:15
 */
@Data
public class GetAnswerListRequest {
    private String questionId;
    private Integer number;

    public static GetAnswerListRequest of(String questionId, Integer number) {
        GetAnswerListRequest request = new GetAnswerListRequest();
        request.setQuestionId(questionId);
        request.setNumber(number == null ? 5 : number);
        return request;
    }
}
