package com.jiac.comment.controller;

import com.jiac.comment.feign.ArticleFeign;
import com.jiac.comment.feign.UserFeign;
import com.jiac.comment.request.AddCommentRequest;
import com.jiac.comment.service.CommentService;
import com.jiac.common.dto.CommentDto;
import com.jiac.common.utils.CommonType;
import com.jiac.common.utils.ErrorEnum;
import com.jiac.common.utils.MyException;
import com.jiac.common.vo.CommentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * FileName: CommentController
 * Author: Jiac
 * Date: 2022/3/10 13:25
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private UserFeign userFeign;

    @Autowired
    private ArticleFeign articleFeign;

    @Autowired
    private CommentService commentService;


    @ResponseBody
    @PostMapping("/addComment")
    private CommonType<CommentVo> addComment(@RequestParam("content") String content,
                                             @RequestParam("username") String username,
                                             @RequestParam("articleId") String articleId) {
        Boolean userExist = userFeign.userExist(username).getData();
        if(!userExist) {
            throw new MyException(ErrorEnum.USER_NOT_EXIST);
        }
        Boolean articleExist = articleFeign.articleExist(articleId).getData();
        if(!articleExist) {
            throw new MyException(ErrorEnum.ARTICLE_NOT_EXIST);
        }
        AddCommentRequest request = AddCommentRequest.of(content, username, articleId);
        CommentDto commentDto = commentService.addComment(request);
        return CommonType.success(CommentVo.of(commentDto), "添加成功");
    }
}
