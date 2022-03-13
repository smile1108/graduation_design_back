package com.jiac.article.request;

import lombok.Data;

/**
 * FileName: SearchArticleRequest
 * Author: Jiac
 * Date: 2022/3/1 15:39
 */
@Data
public class SearchQuestionRequest {
    private String keyword;
    private Integer page;
    private Integer pageSize;

    public static SearchQuestionRequest of(String keyword, Integer page, Integer pageSize) {
        SearchQuestionRequest request = new SearchQuestionRequest();
        request.setKeyword(keyword);
        request.setPage(page);
        request.setPageSize(pageSize);
        return request;
    }
}
