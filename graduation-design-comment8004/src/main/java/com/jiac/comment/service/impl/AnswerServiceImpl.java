package com.jiac.comment.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.jiac.comment.repository.AnswerRepository;
import com.jiac.comment.request.AddAnswerRequest;
import com.jiac.comment.request.DeleteAnswerRequest;
import com.jiac.comment.service.AnswerService;
import com.jiac.common.dto.AnswerDto;
import com.jiac.common.entity.Answer;
import com.jiac.common.entity.Question;
import com.jiac.common.entity.User;
import com.jiac.common.utils.ErrorEnum;
import com.jiac.common.utils.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;

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

    @Override
    public AnswerDto deleteAnswer(DeleteAnswerRequest request) {
        Optional<Answer> answerOptional = answerRepository.findById(request.getId());
        try {
            Answer answer = answerOptional.get();
            if(!answer.getUser().getUsername().equals(request.getUsername())) {
                throw new MyException(ErrorEnum.NO_PERMISSION);
            }
            answerRepository.delete(answer);
            return AnswerDto.of(answer);
        } catch (NoSuchElementException e) {
            throw new MyException(ErrorEnum.ANSWER_NOT_EXIST);
        }
    }
}
