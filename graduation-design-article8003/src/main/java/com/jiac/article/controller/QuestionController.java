package com.jiac.article.controller;

import com.jiac.article.feign.UserFeign;
import com.jiac.article.request.AddQuestionRequest;
import com.jiac.article.request.DeleteQuestionRequest;
import com.jiac.article.service.QuestionService;
import com.jiac.common.dto.QuestionDto;
import com.jiac.common.utils.CommonType;
import com.jiac.common.utils.ErrorEnum;
import com.jiac.common.utils.MyException;
import com.jiac.common.vo.QuestionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
