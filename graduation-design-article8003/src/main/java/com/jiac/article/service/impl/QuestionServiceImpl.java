package com.jiac.article.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.jiac.article.repository.QuestionRepository;
import com.jiac.article.request.AddQuestionRequest;
import com.jiac.article.service.QuestionService;
import com.jiac.common.dto.ArticleDto;
import com.jiac.common.dto.QuestionDto;
import com.jiac.common.entity.Question;
import com.jiac.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * FileName: QuestionServiceImpl
 * Author: Jiac
 * Date: 2022/3/13 11:22
 */
@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public QuestionDto addQuestion(AddQuestionRequest request) {
        Question question = new Question();
        RandomUtil randomUtil = new RandomUtil();
        String randomId = randomUtil.randomString(10);
        question.setId(randomId);
        question.setTitle(request.getTitle());
        question.setContent(request.getContent());
        question.setPublishDate(new Date());
        User user = new User();
        user.setUsername(request.getUsername());
        question.setUser(user);
        Question save = questionRepository.save(question);
        return QuestionDto.of(save);
    }
}
