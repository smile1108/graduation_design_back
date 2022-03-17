package com.jiac.comment.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.jiac.comment.repository.AnswerRepository;
import com.jiac.comment.request.AddAnswerRequest;
import com.jiac.comment.request.DeleteAnswerRequest;
import com.jiac.comment.request.GetAnswerListRequest;
import com.jiac.comment.request.GetUserAnswerListRequest;
import com.jiac.comment.service.AnswerService;
import com.jiac.common.dto.AnswerDto;
import com.jiac.common.entity.*;
import com.jiac.common.utils.ErrorEnum;
import com.jiac.common.utils.MyException;
import com.jiac.common.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    public Boolean deleteAnswerByQuestionId(String questionId) {
        try {
            answerRepository.deleteAnswerByQuestionId(questionId);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public PageVo<AnswerDto> getAnswerListByQuestion(GetAnswerListRequest request) {
        Sort sort = Sort.by(Sort.Direction.DESC, "publishDate");
        int page = 0;
        int pageSize = request.getNumber();
        PageRequest pageRequest = PageRequest.of(page, pageSize, sort);
        String questionId = request.getQuestionId();
        Specification<Answer> specification = (Specification<Answer>) (root, query, cb) -> {
            Join<Answer, Question> questionJoin = root.join("question", JoinType.LEFT);
            return cb.equal(questionJoin.get("id").as(String.class), questionId);
        };
        Page<Answer> answerPage = answerRepository.findAll(specification, pageRequest);
        return transferAnswerPage2AnswerDtoPageVo(answerPage);
    }

    @Override
    public PageVo<AnswerDto> getUserAnswerList(GetUserAnswerListRequest request) {
        Sort sort = Sort.by(Sort.Direction.DESC, "publishDate");
        int page = request.getPage();
        int pageSize = request.getPageSize();
        PageRequest pageRequest = PageRequest.of(page, pageSize, sort);
        String username = request.getUsername();
        Specification<Answer> specification = (Specification<Answer>) (root, query, cb) -> {
            Join<Answer, User> userJoin = root.join("user", JoinType.LEFT);
            return cb.equal(userJoin.get("username").as(String.class), username);
        };
        Page<Answer> answerPage = answerRepository.findAll(specification, pageRequest);
        return transferAnswerPage2AnswerDtoPageVo(answerPage);
    }

    @Override
    public Integer countUserAnswer(String username) {
        return answerRepository.countUserAnswer(username);
    }

    private PageVo<AnswerDto> transferAnswerPage2AnswerDtoPageVo(Page<Answer> answerPage) {
        PageVo<AnswerDto> answerDtoPageVo = new PageVo<>();
        answerDtoPageVo.setLists(answerPage.getContent().stream().map(AnswerDto::of).collect(Collectors.toList()));
        answerDtoPageVo.setCount(answerPage.getTotalElements());
        answerDtoPageVo.setSumPage(answerPage.getTotalPages());
        return answerDtoPageVo;
    }
}
