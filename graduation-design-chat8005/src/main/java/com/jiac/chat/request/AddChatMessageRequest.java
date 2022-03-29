package com.jiac.chat.request;

import lombok.Data;

/**
 * FileName: AddChatMessageReqeust
 * Author: Jiac
 * Date: 2022/3/29 12:56
 */
@Data
public class AddChatMessageRequest {
    private String fromUser;
    private String toUser;
    private String type;
    private String content;

    public static AddChatMessageRequest of(String fromUser, String toUser, String type, String content) {
        AddChatMessageRequest request = new AddChatMessageRequest();
        request.setFromUser(fromUser);
        request.setToUser(toUser);
        request.setType(type);
        request.setContent(content);
        return request;
    }
}
