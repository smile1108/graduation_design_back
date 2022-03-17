package com.jiac.article.feign;

import com.jiac.common.utils.CommonType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * FileName: CommentFeign
 * Author: Jiac
 * Date: 2022/3/14 11:03
 */
@FeignClient(value = "graduation-design-comment8004")
public interface CommentFeign {

    @GetMapping("/comment/countCommentByArticle")
    CommonType<Integer> countCommentByArticle(@RequestParam("articleId") String articleId);

    @GetMapping("/comment/deleteCommentByArticleId")
    CommonType<Boolean> deleteCommentByArticleId(@RequestParam("articleId") String articleId);

    @GetMapping("/comment/answer/deleteAnswerByQuestionId")
    CommonType<Boolean> deleteAnswerByQuestionId(@RequestParam("questionId") String questionId);
}
