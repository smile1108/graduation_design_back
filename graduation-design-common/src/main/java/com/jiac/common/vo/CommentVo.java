package com.jiac.common.vo;

import com.jiac.common.dto.ArticleDto;
import com.jiac.common.dto.CommentDto;
import com.jiac.common.dto.UserDto;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * FileName: CommentVo
 * Author: Jiac
 * Date: 2022/3/10 13:32
 */
@Data
public class CommentVo {
    private String id;

    private String content;

    private Date publishDate;

    private UserVo userVo;

    private ArticleVo articleVo;

    public static CommentVo of(CommentDto commentDto) {
        CommentVo commentVo = new CommentVo();
        BeanUtils.copyProperties(commentDto, commentVo);
        commentVo.setUserVo(UserVo.of(commentDto.getUserDto()));
        commentVo.setArticleVo(ArticleVo.of(commentDto.getArticleDto()));
        return commentVo;
    }
}
