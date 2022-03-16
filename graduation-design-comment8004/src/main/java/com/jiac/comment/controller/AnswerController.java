package com.jiac.comment.controller;

import com.jiac.comment.feign.ArticleFeign;
import com.jiac.comment.feign.UserFeign;
import com.jiac.comment.request.AddAnswerRequest;
import com.jiac.comment.service.AnswerService;
import com.jiac.common.dto.AnswerDto;
import com.jiac.common.utils.CommonType;
import com.jiac.common.utils.ErrorEnum;
import com.jiac.common.utils.MyException;
import com.jiac.common.vo.AnswerVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * FileName: AnswerController
 * Author: Jiac
 * Date: 2022/3/16 9:37
 */
@RestController
@RequestMapping("/comment/answer")
public class AnswerController {

    @Autowired
    private UserFeign userFeign;

    @Autowired
    private ArticleFeign articleFeign;

    @Autowired
    private AnswerService answerService;

    @ResponseBody
    @PostMapping("/addAnswer")
    public CommonType<AnswerVo> addAnswer(@RequestParam("content") String content,
                                          @RequestParam("username") String username,
                                          @RequestParam("questionId") String questionId) {
        if(!userFeign.userExist(username).getData()) {
            throw new MyException(ErrorEnum.USER_NOT_EXIST);
        }
        if(!articleFeign.questionExist(questionId).getData()) {
            throw new MyException(ErrorEnum.QUESTION_NOT_EXIST);
        }
        AddAnswerRequest request = AddAnswerRequest.of(content, username, questionId);
        AnswerDto answerDto = answerService.addAnswer(request);
        return CommonType.success(AnswerVo.of(answerDto), "添加成功");
    }
}
