package com.jiac.common.vo;

import com.jiac.common.dto.AnswerDto;
import com.jiac.common.dto.ArticleDto;
import com.jiac.common.dto.UserDto;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * FileName: AnswerVo
 * Author: Jiac
 * Date: 2022/3/16 9:34
 */
@Data
public class AnswerVo {
    private String id;

    private String content;

    private Date publishDate;

    private UserVo userVo;

    private QuestionVo questionVo;

    private String htmlContent;

    private String textContent;

    public static AnswerVo of(AnswerDto answerDto) {
        AnswerVo answerVo = new AnswerVo();
        BeanUtils.copyProperties(answerDto, answerVo);
        answerVo.setQuestionVo(QuestionVo.of(answerDto.getQuestionDto()));
        answerVo.setUserVo(UserVo.of(answerDto.getUserDto()));
        return answerVo;
    }
}
