package com.jiac.article.controller;

import com.jiac.article.utils.ArticleClassify;
import com.jiac.article.vo.ArticleClassifyVo;
import com.jiac.common.utils.CommonType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * FileName: ArticleController
 * Author: Jiac
 * Date: 2022/2/11 9:35
 */
@RestController
@CrossOrigin
@RequestMapping("/article")
public class ArticleController {

    // 获取文章分类的接口
    @ResponseBody
    @GetMapping("/getArticleClassify")
    public CommonType<List<ArticleClassifyVo>> getArticleClassify() {
        List<ArticleClassify> articleClassifies = Arrays.asList(ArticleClassify.values());
        List<ArticleClassifyVo> vos = articleClassifies.stream().map(ArticleClassifyVo::of).collect(Collectors.toList());
        return CommonType.success(vos, "获取成功");
    }
}
