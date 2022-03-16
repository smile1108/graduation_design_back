package com.jiac.common.dto;

import com.jiac.common.entity.Article;
import com.jiac.common.utils.ArticleClassify;
import com.jiac.common.utils.Html2Text;
import com.jiac.common.utils.Markdown2Html;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * FileName: ArticleDto
 * Author: Jiac
 * Date: 2022/2/13 9:22
 */
@Data
public class ArticleDto {
    private String id;

    private String title;

    private String content;

    private Date publishDate;

    private String classify;

    private UserDto userDto;

    private Boolean like;

    private Boolean follow;

    private String htmlContent;

    private String textContent;

    private Integer likeCount;

    private Integer commentCount;

    public static ArticleDto of(Article article) {
        ArticleDto articleDto = new ArticleDto();
        BeanUtils.copyProperties(article, articleDto);
        articleDto.setUserDto(UserDto.of(article.getUser()));
        articleDto.setHtmlContent(Markdown2Html.convert(articleDto.getContent()));
        articleDto.setTextContent(Html2Text.convert(articleDto.getHtmlContent()));
        articleDto.setLike(false);
        articleDto.setFollow(false);
        return articleDto;
    }
}
