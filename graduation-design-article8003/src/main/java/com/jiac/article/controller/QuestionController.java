package com.jiac.article.controller;

import com.jiac.article.feign.UserFeign;
import com.jiac.article.request.AddQuestionRequest;
import com.jiac.article.request.DeleteQuestionRequest;
import com.jiac.article.request.GetUserQuestionRequest;
import com.jiac.article.request.SearchQuestionRequest;
import com.jiac.article.service.QuestionService;
import com.jiac.common.dto.QuestionDto;
import com.jiac.common.utils.CommonType;
import com.jiac.common.utils.ErrorEnum;
import com.jiac.common.utils.MyException;
import com.jiac.common.vo.PageVo;
import com.jiac.common.vo.QuestionVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

/**
 * FileName: QuestionController
 * Author: Jiac
 * Date: 2022/3/13 11:18
 */
@RestController
@RequestMapping("/article/question")
public class QuestionController {

    @Autowired
    private UserFeign userFeign;

    @Autowired
    private QuestionService questionService;

    @ResponseBody
    @PostMapping("/addQuestion")
    public CommonType<QuestionVo> addQuestion(@RequestParam("title") String title,
                                              @RequestParam("content") String content,
                                              @RequestParam("username") String username) {
        if(!userFeign.userExist(username).getData()) {
            throw new MyException(ErrorEnum.USER_NOT_EXIST);
        }
        AddQuestionRequest request = AddQuestionRequest.of(title, content, username);
        QuestionDto questionDto = questionService.addQuestion(request);
        return CommonType.success(QuestionVo.of(questionDto), "添加成功");
    }

    @ResponseBody
    @GetMapping("/deleteQuestion")
    public CommonType<QuestionVo> deleteQuestion(@RequestParam("id") String id,
                                                 @RequestParam("username") String username) {
        if(!userFeign.userExist(username).getData()) {
            throw new MyException(ErrorEnum.USER_NOT_EXIST);
        }
        DeleteQuestionRequest request = DeleteQuestionRequest.of(id, username);
        QuestionDto questionDto = questionService.deleteQuestion(request);
        return CommonType.success(QuestionVo.of(questionDto), "删除成功");
    }

    @ResponseBody
    @GetMapping("/countQuestionByUser")
    public CommonType<Integer> countQuestionByUser(@RequestParam("username") String username) {
        if(!userFeign.userExist(username).getData()) {
            throw new MyException(ErrorEnum.USER_NOT_EXIST);
        }
        return CommonType.success(questionService.countQuestionByUser(username), "查询成功");
    }

    @ResponseBody
    @GetMapping("/searchQuestion")
    public CommonType<PageVo<QuestionVo>> searchQuestion(@RequestParam(value = "page", required = false) Integer page,
                                                         @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                         @RequestParam(value = "keyword", required = false) String keyword) {
        SearchQuestionRequest request = SearchQuestionRequest.of(keyword, page, pageSize);
        PageVo<QuestionDto> questionDtoPageVo = questionService.searchQuestion(request);
        return CommonType.success(transferQuestionDtoPageVo2QuestionVoPageVo(questionDtoPageVo), "查询成功");
    }

    @ResponseBody
    @GetMapping("/getUserQuestionList")
    public CommonType<PageVo<QuestionVo>> getUserQuestionList(@RequestParam("username") String username,
                                                              @RequestParam(value = "page", required = false) Integer page,
                                                              @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        if(!userFeign.userExist(username).getData()) {
            throw new MyException(ErrorEnum.USER_NOT_EXIST);
        }
        GetUserQuestionRequest request = GetUserQuestionRequest.of(username, page, pageSize);
        return CommonType.success(transferQuestionDtoPageVo2QuestionVoPageVo(questionService.getUserQuestionList(request)), "查询成功");
    }

    @ResponseBody
    @GetMapping("/getQuestionMessageById")
    public CommonType<QuestionVo> getQuestionMessageById(@RequestParam("questionId") String questionId) {
        QuestionDto questionDto = questionService.getQuestionMessageById(questionId);
        return CommonType.success(QuestionVo.of(questionDto), "查询成功");
    }

    private PageVo<QuestionVo> transferQuestionDtoPageVo2QuestionVoPageVo(PageVo<QuestionDto> questionDtoPageVo) {
        PageVo<QuestionVo> questionVoPageVo = new PageVo<>();
        BeanUtils.copyProperties(questionDtoPageVo, questionVoPageVo);
        questionVoPageVo.setLists(questionDtoPageVo.getLists().stream().map(QuestionVo::of).collect(Collectors.toList()));
        return questionVoPageVo;
    }
}
