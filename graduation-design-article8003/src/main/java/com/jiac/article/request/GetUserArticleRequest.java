package com.jiac.article.request;

import lombok.Data;

/**
 * FileName: GetUserArticleRequest
 * Author: Jiac
 * Date: 2022/2/16 7:12
 */
@Data
public class GetUserArticleRequest {
    private String username;
    private Integer page;
    private Integer pageSize;

    public static GetUserArticleRequest of(String username, Integer page, Integer pageSize) {
        GetUserArticleRequest request = new GetUserArticleRequest();
        request.setUsername(username);
        request.setPage(page);
        request.setPageSize(pageSize);
        return request;
    }
}
