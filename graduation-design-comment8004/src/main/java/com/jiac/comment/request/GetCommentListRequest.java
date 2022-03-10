package com.jiac.comment.request;

import lombok.Data;

/**
 * FileName: GetCommentListRequest
 * Author: Jiac
 * Date: 2022/3/10 15:42
 */
@Data
public class GetCommentListRequest {
    private String articleId;
    private Integer number;

    public static GetCommentListRequest of(String articleId, Integer number) {
        GetCommentListRequest request = new GetCommentListRequest();
        request.setArticleId(articleId);
        request.setNumber(number);
        return request;
    }
}
