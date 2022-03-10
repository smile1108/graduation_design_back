package com.jiac.comment.service;

import com.jiac.comment.request.AddCommentRequest;
import com.jiac.comment.request.GetCommentListRequest;
import com.jiac.common.dto.CommentDto;
import com.jiac.common.vo.PageVo;

/**
 * FileName: CommentService
 * Author: Jiac
 * Date: 2022/3/10 13:47
 */
public interface CommentService {
    CommentDto addComment(AddCommentRequest request);

    PageVo<CommentDto> getCommentList(GetCommentListRequest request);
}
