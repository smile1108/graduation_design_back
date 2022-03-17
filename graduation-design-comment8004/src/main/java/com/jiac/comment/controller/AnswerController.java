package com.jiac.comment.controller;

import com.jiac.comment.feign.ArticleFeign;
import com.jiac.comment.feign.UserFeign;
import com.jiac.comment.request.AddAnswerRequest;
import com.jiac.comment.request.DeleteAnswerRequest;
import com.jiac.comment.request.GetAnswerListRequest;
import com.jiac.comment.request.GetUserAnswerListRequest;
import com.jiac.comment.service.AnswerService;
import com.jiac.common.dto.AnswerDto;
import com.jiac.common.utils.CommonType;
import com.jiac.common.utils.ErrorEnum;
import com.jiac.common.utils.MyException;
import com.jiac.common.vo.AnswerVo;
import com.jiac.common.vo.PageVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

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

    @ResponseBody
    @GetMapping("/deleteAnswer")
    public CommonType<AnswerVo> deleteAnswer(@RequestParam("id") String id,
                                             @RequestParam("username") String username) {
        if(!userFeign.userExist(username).getData()) {
            throw new MyException(ErrorEnum.USER_NOT_EXIST);
        }
        DeleteAnswerRequest request = DeleteAnswerRequest.of(id, username);
        AnswerDto answerDto = answerService.deleteAnswer(request);
        return CommonType.success(AnswerVo.of(answerDto), "删除成功");
    }

    @ResponseBody
    @GetMapping("/deleteAnswerByQuestionId")
    public CommonType<Boolean> deleteAnswerByQuestionId(@RequestParam("questionId") String questionId) {
        return CommonType.success(answerService.deleteAnswerByQuestionId(questionId), "操作成功");
    }

    @ResponseBody
    @GetMapping("/getAnswerListByQuestion")
    public CommonType<PageVo<AnswerVo>> getAnswerListByQuestion(@RequestParam("questionId") String questionId,
                                                                 @RequestParam(value = "number", required = false) Integer number) {
        if(!articleFeign.questionExist(questionId).getData()) {
            throw new MyException(ErrorEnum.QUESTION_NOT_EXIST);
        }
        GetAnswerListRequest request = GetAnswerListRequest.of(questionId, number);
        PageVo<AnswerDto> answerDtoPageVo = answerService.getAnswerListByQuestion(request);
        return CommonType.success(transferAnswerDtoPageVo2AnswerVoPageVo(answerDtoPageVo), "查询成功");
    }

    @ResponseBody
    @GetMapping("/getUserAnswerList")
    public CommonType<PageVo<AnswerVo>> getUserAnswerList(@RequestParam("username") String username,
                                                          @RequestParam(value = "page", required = false) Integer page,
                                                          @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        if(!userFeign.userExist(username).getData()) {
            throw new MyException(ErrorEnum.USER_NOT_EXIST);
        }
        GetUserAnswerListRequest request = GetUserAnswerListRequest.of(username, page, pageSize);
        PageVo<AnswerDto> answerDtoPageVo = answerService.getUserAnswerList(request);
        return CommonType.success(transferAnswerDtoPageVo2AnswerVoPageVo(answerDtoPageVo), "查询成功");
    }

    @ResponseBody
    @GetMapping("/countUserAnswer")
    public CommonType<Integer> countUserAnswer(@RequestParam("username") String username) {
        return CommonType.success(answerService.countUserAnswer(username), "查询成功");
    }

    private PageVo<AnswerVo> transferAnswerDtoPageVo2AnswerVoPageVo(PageVo<AnswerDto> answerDtoPageVo) {
        PageVo<AnswerVo> answerVoPageVo = new PageVo<>();
        BeanUtils.copyProperties(answerDtoPageVo, answerVoPageVo);
        answerVoPageVo.setLists(answerDtoPageVo.getLists().stream().map(AnswerVo::of).collect(Collectors.toList()));
        return answerVoPageVo;
    }
}
