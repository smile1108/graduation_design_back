package com.jiac.article.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.jiac.article.feign.CommentFeign;
import com.jiac.article.feign.UserFeign;
import com.jiac.article.repository.QuestionRepository;
import com.jiac.article.request.AddQuestionRequest;
import com.jiac.article.request.DeleteQuestionRequest;
import com.jiac.article.request.GetUserQuestionRequest;
import com.jiac.article.request.SearchQuestionRequest;
import com.jiac.article.service.QuestionService;
import com.jiac.common.utils.Html2Text;
import com.jiac.common.utils.Markdown2Html;
import com.jiac.common.dto.QuestionDto;
import com.jiac.common.entity.Question;
import com.jiac.common.entity.User;
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
import javax.persistence.criteria.Predicate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * FileName: QuestionServiceImpl
 * Author: Jiac
 * Date: 2022/3/13 11:22
 */
@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserFeign userFeign;

    @Autowired
    private CommentFeign commentFeign;

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

    @Override
    public QuestionDto deleteQuestion(DeleteQuestionRequest request) {
        Optional<Question> questionOptional = questionRepository.findById(request.getId());
        try {
            Question question = questionOptional.get();
            if(!question.getUser().getUsername().equals(request.getUsername())) {
                throw new MyException(ErrorEnum.NO_PERMISSION);
            }
            if(commentFeign.deleteAnswerByQuestionId(request.getId()).getData()) {
                questionRepository.delete(question);
            }
            return QuestionDto.of(question);
        } catch (NoSuchElementException e) {
            throw new MyException(ErrorEnum.QUESTION_NOT_EXIST);
        }
    }

    @Override
    public Integer countQuestionByUser(String username) {
        return questionRepository.countQuestionByUser(username);
    }

    @Override
    public PageVo<QuestionDto> searchQuestion(SearchQuestionRequest request) {
        Sort sort = Sort.by(Sort.Direction.DESC, "publishDate");
        int page = request.getPage() == null ? 0 : request.getPage();
        int pageSize = request.getPageSize() == null ? 5 : request.getPageSize();
        PageRequest pageRequest = PageRequest.of(page, pageSize, sort);
        String keyword = request.getKeyword();
        Specification<Question> specification = (Specification<Question>) (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (keyword != null && !"".equals(keyword)) {
                Join<Question, User> userJoin = root.join("user", JoinType.LEFT);
                Predicate keywordPredicate = cb.or(cb.like(root.get("title"), "%" + keyword + "%"), cb.like(root.get("content"), "%" + keyword + "%"), cb.like(userJoin.get("nickname"), "%" + keyword + "%"));
                predicateList.add(keywordPredicate);
            }
            Predicate[] predicates = new Predicate[predicateList.size()];
            return query.where(predicateList.toArray(predicates)).getRestriction();
        };
        Page<Question> questionPage = questionRepository.findAll(specification, pageRequest);
        return transferQuestionPage2QuestionDtoPageVo(questionPage);
    }

    @Override
    public PageVo<QuestionDto> getUserQuestionList(GetUserQuestionRequest request) {
        Sort sort = Sort.by(Sort.Direction.DESC, "publishDate");
        int page = request.getPage();
        int pageSize = request.getPageSize();
        PageRequest pageRequest = PageRequest.of(page, pageSize, sort);
        String username = request.getUsername();
        Specification<Question> specification = (Specification<Question>) (root, query, cb) -> {
            Join<Question, User> userJoin = root.join("user", JoinType.LEFT);
            return cb.equal(userJoin.get("username").as(String.class), username);
        };
        Page<Question> questionPage = questionRepository.findAll(specification, pageRequest);
        return transferQuestionPage2QuestionDtoPageVo(questionPage);
    }

    @Override
    public QuestionDto getQuestionMessageById(String questionId, String username) {
        Optional<Question> questionOptional = questionRepository.findById(questionId);
        try {
            Question question = questionOptional.get();
            QuestionDto questionDto = QuestionDto.of(question);
            questionDto.setHtmlContent(Markdown2Html.convert(questionDto.getContent()));
            questionDto.setTextContent(Html2Text.convert(questionDto.getHtmlContent()));
            if(username != null && !"".equals(username)) {
                questionDto.setFollow(userFeign.getUserFollow(username, questionDto.getUserDto().getUsername()).getData());
            }
            return questionDto;
        }catch (NoSuchElementException e) {
            throw new MyException(ErrorEnum.QUESTION_NOT_EXIST);
        }
    }

    @Override
    public Boolean questionExist(String questionId) {
        Optional<Question> questionOptional = questionRepository.findById(questionId);
        try {
            questionOptional.get();
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    private PageVo<QuestionDto> transferQuestionPage2QuestionDtoPageVo(Page<Question> questionPage) {
        PageVo<QuestionDto> questionDtoPageVo = new PageVo<>();
        questionDtoPageVo.setLists(questionPage.getContent().stream().map(question -> {
            QuestionDto questionDto = QuestionDto.of(question);
            questionDto.setFollowCount(userFeign.countFollowed(questionDto.getUserDto().getUsername()).getData());
            questionDto.setAnswerCount(commentFeign.countAnswerByQuestion(questionDto.getId()).getData());
            return questionDto;
        }).collect(Collectors.toList()));
        questionDtoPageVo.setSumPage(questionPage.getTotalPages());
        questionDtoPageVo.setCount(questionPage.getTotalElements());
        return questionDtoPageVo;
    }
}
