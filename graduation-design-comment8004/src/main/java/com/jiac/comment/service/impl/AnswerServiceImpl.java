package com.jiac.comment.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.jiac.comment.repository.AnswerRepository;
import com.jiac.comment.request.AddAnswerRequest;
import com.jiac.comment.service.AnswerService;
import com.jiac.common.dto.AnswerDto;
import com.jiac.common.entity.Answer;
import com.jiac.common.entity.Question;
import com.jiac.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * FileName: AnswerServiceImpl
 * Author: Jiac
 * Date: 2022/3/16 9:50
 */
@Service
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Override
    public AnswerDto addAnswer(AddAnswerRequest request) {
        Answer answer = new Answer();
        answer.setId(RandomUtil.randomString(10));
        answer.setContent(request.getContent());
        answer.setPublishDate(new Date());
        User user = new User();
        user.setUsername(request.getUsername());
        answer.setUser(user);
        Question question = new Question();
        question.setId(request.getQuestionId());
        answer.setQuestion(question);
        Answer save = answerRepository.save(answer);
        return AnswerDto.of(save);
    }
}
