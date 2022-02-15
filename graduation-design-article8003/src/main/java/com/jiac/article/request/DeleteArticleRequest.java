package com.jiac.article.request;

import lombok.Data;

/**
 * FileName: DeleteArticleRequest
 * Author: Jiac
 * Date: 2022/2/16 6:46
 */
@Data
public class DeleteArticleRequest {
    private String id;
    private String username;

    public static DeleteArticleRequest of(String id, String username) {
        DeleteArticleRequest request = new DeleteArticleRequest();
        request.setId(id);
        request.setUsername(username);
        return request;
    }
}
