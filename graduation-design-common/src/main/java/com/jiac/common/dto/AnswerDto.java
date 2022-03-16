package com.jiac.common.dto;

import com.jiac.common.entity.Answer;
import com.jiac.common.entity.Article;
import com.jiac.common.entity.User;
import com.jiac.common.utils.Html2Text;
import com.jiac.common.utils.Markdown2Html;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.util.Date;

/**
 * FileName: AnswerDto
 * Author: Jiac
 * Date: 2022/3/16 9:31
 */
@Data
public class AnswerDto {
    private String id;

    private String content;

    private Date publishDate;

    private UserDto userDto;

    private QuestionDto questionDto;

    private String htmlContent;

    private String textContent;

    public static AnswerDto of(Answer answer) {
        AnswerDto answerDto = new AnswerDto();
        BeanUtils.copyProperties(answer, answerDto);
        answerDto.setQuestionDto(QuestionDto.of(answer.getQuestion()));
        answerDto.setUserDto(UserDto.of(answer.getUser()));
        answerDto.setHtmlContent(Markdown2Html.convert(answerDto.getContent()));
        answerDto.setTextContent(Html2Text.convert(answerDto.getHtmlContent()));
        return answerDto;
    }
}
