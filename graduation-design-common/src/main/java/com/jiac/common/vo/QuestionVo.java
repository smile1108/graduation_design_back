package com.jiac.common.vo;

import com.jiac.common.dto.QuestionDto;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * FileName: QuestionVo
 * Author: Jiac
 * Date: 2022/3/13 11:16
 */
@Data
public class QuestionVo {

    private String id;

    private String title;

    private String content;

    private Date publishDate;

    private String htmlContent;

    private String textContent;

    private UserVo userVo;

    public static QuestionVo of(QuestionDto questionDto) {
        QuestionVo questionVo = new QuestionVo();
        BeanUtils.copyProperties(questionDto, questionVo);
        questionVo.setUserVo(UserVo.of(questionDto.getUserDto()));
        return questionVo;
    }
}
