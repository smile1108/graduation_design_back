package com.jiac.article.controller;

import com.jiac.article.service.ArticleService;
import com.jiac.article.utils.ArticleClassify;
import com.jiac.article.vo.ArticleClassifyVo;
import com.jiac.common.utils.CommonType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;


    // 获取文章分类的接口
    @ResponseBody
    @GetMapping("/getArticleClassify")
    public CommonType<List<ArticleClassifyVo>> getArticleClassify() {
        List<ArticleClassify> articleClassifies = Arrays.asList(ArticleClassify.values());
        List<ArticleClassifyVo> vos = articleClassifies.stream().map(ArticleClassifyVo::of).collect(Collectors.toList());
        return CommonType.success(vos, "获取成功");
    }

    // 用户编写文章时 上传图片的接口
    @ResponseBody
    @PostMapping("/uploadImage")
    public CommonType<String> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        // 这个接口只需要存储对应的图片 并返回给前端一个在服务器上的图片的路径 不需要存入数据库
        String imagePath = articleService.uploadImage(file);
        // 然后直接返回给前端这个图片的访问路径
        return CommonType.success(imagePath, "上传成功");
    }
}
