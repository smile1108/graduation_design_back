package com.jiac.common.dto;

import com.jiac.common.entity.Article;
import com.jiac.common.utils.ArticleClassify;
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

    public static ArticleDto of(Article article) {
        ArticleDto articleDto = new ArticleDto();
        BeanUtils.copyProperties(article, articleDto);
        articleDto.setUserDto(UserDto.of(article.getUser()));
        articleDto.setLike(false);
        return articleDto;
    }
}
