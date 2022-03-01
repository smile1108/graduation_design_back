package com.jiac.article.request;

import lombok.Data;

/**
 * FileName: SearchArticleRequest
 * Author: Jiac
 * Date: 2022/3/1 15:39
 */
@Data
public class SearchArticleRequest {
    private String keyword;
    private String classify;
    private Integer page;
    private Integer pageSize;

    public static SearchArticleRequest of(String keyword, String classify, Integer page, Integer pageSize) {
        SearchArticleRequest request = new SearchArticleRequest();
        request.setKeyword(keyword);
        request.setClassify(classify);
        request.setPage(page);
        request.setPageSize(pageSize);
        return request;
    }
}
