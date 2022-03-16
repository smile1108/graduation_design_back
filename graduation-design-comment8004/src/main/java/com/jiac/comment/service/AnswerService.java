package com.jiac.comment.service;

import com.jiac.comment.request.AddAnswerRequest;
import com.jiac.comment.request.DeleteAnswerRequest;
import com.jiac.comment.request.GetAnswerListRequest;
import com.jiac.common.dto.AnswerDto;
import com.jiac.common.vo.PageVo;

/**
 * FileName: AnswerService
 * Author: Jiac
 * Date: 2022/3/16 9:50
 */
public interface AnswerService {
    AnswerDto addAnswer(AddAnswerRequest request);

    AnswerDto deleteAnswer(DeleteAnswerRequest request);

    PageVo<AnswerDto> getAnswerListByQuestion(GetAnswerListRequest request);
}
