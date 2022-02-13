package com.jiac.common.dto;

import com.jiac.common.utils.ArticleClassify;
import lombok.Data;

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

    private ArticleClassify classify;

    private UserDto user;
}
