package com.jiac.common.dto;

import com.jiac.common.entity.Question;
import com.jiac.common.entity.User;
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

    public static QuestionDto of(Question question) {
        QuestionDto questionDto = new QuestionDto();
        BeanUtils.copyProperties(question, questionDto);
        questionDto.setUserDto(UserDto.of(question.getUser()));
        return questionDto;
    }
}
