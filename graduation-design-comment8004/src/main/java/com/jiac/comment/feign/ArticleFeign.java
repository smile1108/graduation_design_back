package com.jiac.comment.feign;

import com.jiac.common.utils.CommonType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * FileName: ArticleFeign
 * Author: Jiac
 * Date: 2022/3/10 13:38
 */
@FeignClient(value = "graduation-design-article8003")
public interface ArticleFeign {

    @GetMapping(value = "/article/articleExist")
    CommonType<Boolean> articleExist(@RequestParam("articleId") String articleId);
}
