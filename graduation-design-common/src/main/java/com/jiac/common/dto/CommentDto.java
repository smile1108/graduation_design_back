package com.jiac.common.dto;

import com.jiac.common.entity.Article;
import com.jiac.common.entity.Comment;
import com.jiac.common.entity.User;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.util.Date;

/**
 * FileName: CommentDto
 * Author: Jiac
 * Date: 2022/3/10 13:31
 */
@Data
public class CommentDto {
    private String id;

    private String content;

    private Date publishDate;

    private UserDto userDto;

    private ArticleDto articleDto;

    public static CommentDto of(Comment comment) {
        CommentDto commentDto = new CommentDto();
        BeanUtils.copyProperties(comment, commentDto);
        commentDto.setUserDto(UserDto.of(comment.getUser()));
        commentDto.setArticleDto(ArticleDto.of(comment.getArticle()));
        return commentDto;
    }
}
