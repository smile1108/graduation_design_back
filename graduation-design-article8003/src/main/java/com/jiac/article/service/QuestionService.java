package com.jiac.article.service;

import com.jiac.article.request.AddQuestionRequest;
import com.jiac.article.request.DeleteQuestionRequest;
import com.jiac.common.dto.QuestionDto;

/**
 * FileName: QuestionService
 * Author: Jiac
 * Date: 2022/3/13 11:22
 */
public interface QuestionService {
    QuestionDto addQuestion(AddQuestionRequest request);

    QuestionDto deleteQuestion(DeleteQuestionRequest request);

    Integer countQuestionByUser(String username);
}
