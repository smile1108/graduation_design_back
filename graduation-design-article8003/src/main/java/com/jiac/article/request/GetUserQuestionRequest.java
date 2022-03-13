package com.jiac.article.request;

import lombok.Data;

/**
 * FileName: GetUserArticleRequest
 * Author: Jiac
 * Date: 2022/2/16 7:12
 */
@Data
public class GetUserQuestionRequest {
    private String username;
    private Integer page;
    private Integer pageSize;

    public static GetUserQuestionRequest of(String username, Integer page, Integer pageSize) {
        GetUserQuestionRequest request = new GetUserQuestionRequest();
        request.setUsername(username);
        request.setPage(page == null ? 0 : page);
        request.setPageSize(pageSize == null ? 5 : pageSize);
        return request;
    }
}
