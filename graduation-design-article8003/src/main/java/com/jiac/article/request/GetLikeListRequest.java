package com.jiac.article.request;

import lombok.Data;

/**
 * FileName: GetLikeListRequest
 * Author: Jiac
 * Date: 2022/3/4 9:54
 */
@Data
public class GetLikeListRequest {
    private String username;
    private Integer page;
    private Integer pageSize;

    public static GetLikeListRequest of(String username, Integer page, Integer pageSize) {
        GetLikeListRequest request = new GetLikeListRequest();
        request.setUsername(username);
        request.setPage(page == null ? 0 : page);
        request.setPageSize(pageSize == null ? 5 : pageSize);
        return request;
    }
}
