package com.jiac.comment.request;

import com.jiac.comment.feign.UserFeign;
import com.jiac.common.utils.ErrorEnum;
import com.jiac.common.utils.MyException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * FileName: AddCommentRequest
 * Author: Jiac
 * Date: 2022/3/10 13:42
 */
@Data
public class AddCommentRequest {

    private String content;
    private String username;
    private String articleId;

    public static AddCommentRequest of(String content, String username, String articleId) {
        if(content.length() > 300) {
            throw new MyException(ErrorEnum.COMMENT_CONTENT_TOO_LONG);
        }
        AddCommentRequest request = new AddCommentRequest();
        request.setContent(content);
        request.setUsername(username);
        request.setArticleId(articleId);
        return request;
    }
}
