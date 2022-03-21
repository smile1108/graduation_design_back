package com.jiac.common.dto;

import com.jiac.common.entity.Question;
import com.jiac.common.entity.User;
import com.jiac.common.utils.Html2Text;
import com.jiac.common.utils.Markdown2Html;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.util.Date;

/**
 * FileName: QuestionDto
 * Author: Jiac
 * Date: 2022/3/13 11:15
 */
@Data
public class QuestionDto {

    private String id;

    private String title;

    private String content;

    private Date publishDate;

    private String htmlContent;

    private String textContent;

    private UserDto userDto;

    private Boolean follow;

    private Integer followCount;

    private Integer answerCount;

    public static QuestionDto of(Question question) {
        QuestionDto questionDto = new QuestionDto();
        BeanUtils.copyProperties(question, questionDto);
        questionDto.setUserDto(UserDto.of(question.getUser()));
        questionDto.setHtmlContent(Markdown2Html.convert(questionDto.getContent()));
        questionDto.setTextContent(Html2Text.convert(questionDto.getHtmlContent()));
        questionDto.setFollow(false);
        return questionDto;
    }
}
