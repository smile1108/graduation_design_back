package com.jiac.chat.request;

import lombok.Data;

/**
 * FileName: GetChatMessageListRequest
 * Author: Jiac
 * Date: 2022/3/30 10:24
 */
@Data
public class GetChatMessageListRequest {
    private String username1;
    private String username2;
    private Integer number;

    public static GetChatMessageListRequest of(String username1, String username2, Integer number) {
        GetChatMessageListRequest request = new GetChatMessageListRequest();
        request.setUsername1(username1);
        request.setUsername2(username2);
        request.setNumber(number == null ? 5 : number);
        return request;
    }
}
