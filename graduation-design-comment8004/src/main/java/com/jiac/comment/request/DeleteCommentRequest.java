package com.jiac.comment.request;

import lombok.Data;

/**
 * FileName: DeleteCommentRequest
 * Author: Jiac
 * Date: 2022/3/11 10:01
 */
@Data
public class DeleteCommentRequest {
    private String commentId;
    private String username;

    public static DeleteCommentRequest of(String commentId, String username) {
        DeleteCommentRequest request = new DeleteCommentRequest();
        request.setCommentId(commentId);
        request.setUsername(username);
        return request;
    }
}
