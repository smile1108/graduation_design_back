package com.jiac.user.feign;

import com.jiac.common.utils.CommonType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * FileName: ArticleFeign
 * Author: Jiac
 * Date: 2022/3/6 10:36
 */
@FeignClient(value = "graduation-design-article8003")
public interface ArticleFeign {

    @GetMapping("/article/countArticleByUser")
    CommonType<Integer> countArticle(@RequestParam("username") String username);
}
