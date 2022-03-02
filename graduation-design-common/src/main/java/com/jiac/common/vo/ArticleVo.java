package com.jiac.common.vo;

import com.jiac.common.dto.ArticleDto;
import com.jiac.common.dto.UserDto;
import com.jiac.common.utils.ArticleClassify;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * FileName: ArticleVo
 * Author: Jiac
 * Date: 2022/2/13 9:30
 */
@Data
public class ArticleVo {
    private String id;

    private String title;

    private String content;

    private Date publishDate;

    private ArticleClassifyVo classifyVo;

    private UserVo userVo;

    private Boolean like;

    public static ArticleVo of(ArticleDto articleDto) {
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(articleDto, articleVo);
        articleVo.setClassifyVo(ArticleClassifyVo.of(ArticleClassify.valueOf(articleDto.getClassify())));
        articleVo.setUserVo(UserVo.of(articleDto.getUserDto()));
        return articleVo;
    }
}
