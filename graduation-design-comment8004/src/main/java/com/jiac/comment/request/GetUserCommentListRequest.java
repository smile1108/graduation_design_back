package com.jiac.comment.request;

import lombok.Data;

/**
 * FileName: GetUserCommentListRequest
 * Author: Jiac
 * Date: 2022/3/11 10:24
 */
@Data
public class GetUserCommentListRequest {
    private String username;
    private Integer page;
    private Integer pageSize;

    public static GetUserCommentListRequest of(String username, Integer page, Integer pageSize) {
        GetUserCommentListRequest request = new GetUserCommentListRequest();
        request.setUsername(username);
        request.setPage(page == null ? 0 : page);
        request.setPageSize(pageSize == null ? 5 : pageSize);
        return request;
    }
}
