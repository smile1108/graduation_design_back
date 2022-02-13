package com.jiac.article.request;

import com.jiac.article.feign.UserFeign;
import com.jiac.common.utils.ArticleClassify;
import com.jiac.common.utils.ErrorEnum;
import com.jiac.common.utils.MyException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * FileName: AddArticleRequest
 * Author: Jiac
 * Date: 2022/2/13 9:35
 */
@Data
public class AddArticleRequest {
    private String title;

    private String classify;

    private String content;

    private String username;

    public static AddArticleRequest of(String title, String classify,
                                       String content, String username) {
        if(title == null || title.length() > 20) {
            throw new MyException(ErrorEnum.ARTICLE_TITLE_TOO_LONG);
        }
        if(classify == null || classify.length() > 20) {
            throw new MyException(ErrorEnum.ARTICLE_CLASSIFY_TOO_LONG);
        }
        try {
            ArticleClassify.valueOf(classify);
        } catch (IllegalArgumentException e) {
            throw new MyException(ErrorEnum.ILLEGAL_CLASSIFY);
        }
        AddArticleRequest request = new AddArticleRequest();
        request.setTitle(title);
        request.setClassify(classify);
        request.setContent(content);
        request.setUsername(username);
        return request;
    }
}
