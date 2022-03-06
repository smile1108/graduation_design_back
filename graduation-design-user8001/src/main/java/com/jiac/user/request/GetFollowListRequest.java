package com.jiac.user.request;

import lombok.Data;

/**
 * FileName: GetFollowListRequest
 * Author: Jiac
 * Date: 2022/3/6 10:19
 */
@Data
public class GetFollowListRequest {
    private String username;
    private Integer page;
    private Integer pageSize;

    public static GetFollowListRequest of(String username, Integer page, Integer pageSize) {
        GetFollowListRequest request = new GetFollowListRequest();
        request.setUsername(username);
        request.setPage(page == null ? 0 : page);
        request.setPageSize(pageSize == null ? 5 : page);
        return request;
    }
}
