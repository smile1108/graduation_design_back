package com.jiac.comment.request;

import lombok.Data;

/**
 * FileName: getUserAnswerListRequest
 * Author: Jiac
 * Date: 2022/3/16 10:43
 */
@Data
public class GetUserAnswerListRequest {
    private String username;
    private Integer page;
    private Integer pageSize;

    public static GetUserAnswerListRequest of(String username, Integer page, Integer pageSize) {
        GetUserAnswerListRequest request = new GetUserAnswerListRequest();
        request.setUsername(username);
        request.setPage(page == null ? 0 : page);
        request.setPageSize(pageSize == null ? 5 : pageSize);
        return request;
    }
}
