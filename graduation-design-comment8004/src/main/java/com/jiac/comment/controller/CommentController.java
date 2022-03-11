package com.jiac.comment.controller;

import com.jiac.comment.feign.ArticleFeign;
import com.jiac.comment.feign.UserFeign;
import com.jiac.comment.request.AddCommentRequest;
import com.jiac.comment.request.DeleteCommentRequest;
import com.jiac.comment.request.GetCommentListRequest;
import com.jiac.comment.request.GetUserCommentListRequest;
import com.jiac.comment.service.CommentService;
import com.jiac.common.dto.CommentDto;
import com.jiac.common.utils.CommonType;
import com.jiac.common.utils.ErrorEnum;
import com.jiac.common.utils.MyException;
import com.jiac.common.vo.CommentVo;
import com.jiac.common.vo.PageVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

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

    @ResponseBody
    @GetMapping("/deleteComment")
    public CommonType<CommentVo> deleteComment(@RequestParam("commentId") String commentId,
                                               @RequestParam("username") String username) {
        if(!userFeign.userExist(username).getData()) {
            throw new MyException(ErrorEnum.USER_NOT_EXIST);
        }
        DeleteCommentRequest request = DeleteCommentRequest.of(commentId, username);
        CommentDto commentDto = commentService.deleteComment(request);
        return CommonType.success(CommentVo.of(commentDto), "删除成功");
    }

    @ResponseBody
    @GetMapping("/getCommentListByArticleId")
    public CommonType<PageVo<CommentVo>> getCommentListByArticleId(@RequestParam("articleId") String articleId,
                                                                   @RequestParam("number") Integer number) {
        if(!articleFeign.articleExist(articleId).getData()) {
            throw new MyException(ErrorEnum.ARTICLE_NOT_EXIST);
        }
        GetCommentListRequest request = GetCommentListRequest.of(articleId, number);
        PageVo<CommentDto> commentDtoPageVo = commentService.getCommentList(request);
        return CommonType.success(transferCommentDtoPageVo2CommentVoPageVo(commentDtoPageVo), "获取成功");
    }

    @ResponseBody
    @GetMapping("/getCommentListByUser")
    public CommonType<PageVo<CommentVo>> getCommentListByUser(@RequestParam("username") String username,
                                                              @RequestParam(value = "page", required = false) Integer page,
                                                              @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        if(!userFeign.userExist(username).getData()) {
            throw new MyException(ErrorEnum.USER_NOT_EXIST);
        }
        GetUserCommentListRequest request = GetUserCommentListRequest.of(username, page, pageSize);
        PageVo<CommentDto> commentDtoPageVo = commentService.getUserCommentList(request);
        return CommonType.success(transferCommentDtoPageVo2CommentVoPageVo(commentDtoPageVo), "查询成功");
    }

    @ResponseBody
    @GetMapping("/countCommentByUser")
    public CommonType<Integer> countCommentByUser(@RequestParam("username") String username) {
        return CommonType.success(commentService.countCommentByUser(username), "查询成功");
    }

    private PageVo<CommentVo> transferCommentDtoPageVo2CommentVoPageVo(PageVo<CommentDto> commentDtoPageVo) {
        PageVo<CommentVo> commentVoPageVo = new PageVo<>();
        BeanUtils.copyProperties(commentDtoPageVo, commentVoPageVo);
        commentVoPageVo.setLists(commentDtoPageVo.getLists().stream().map(CommentVo::of).collect(Collectors.toList()));
        return commentVoPageVo;
    }
}
