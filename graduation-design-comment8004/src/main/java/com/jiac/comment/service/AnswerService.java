package com.jiac.comment.service;

import com.jiac.comment.request.AddAnswerRequest;
import com.jiac.common.dto.AnswerDto;

/**
 * FileName: AnswerService
 * Author: Jiac
 * Date: 2022/3/16 9:50
 */
public interface AnswerService {
    AnswerDto addAnswer(AddAnswerRequest request);
}
