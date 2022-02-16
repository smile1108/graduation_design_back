package com.jiac.article.controller;

import com.jiac.article.feign.UserFeign;
import com.jiac.article.request.AddArticleRequest;
import com.jiac.article.request.DeleteArticleRequest;
import com.jiac.article.request.GetUserArticleRequest;
import com.jiac.article.service.ArticleService;
import com.jiac.common.dto.ArticleDto;
import com.jiac.common.entity.Article;
import com.jiac.common.utils.ErrorEnum;
import com.jiac.common.vo.ArticleClassifyVo;
import com.jiac.common.utils.ArticleClassify;
import com.jiac.common.utils.CommonType;
import com.jiac.common.vo.ArticleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @Autowired
    private UserFeign userFeign;


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

    // 后端删除图片的接口 前端用户可能会删除上传错误的图片 这时候调用删除接口删除图片 以免服务器中存储大量无用的图片浪费空间
    @ResponseBody
    @PostMapping("/deleteImage")
    public CommonType<Boolean> deleteImage(@RequestParam("filename") String filename) {
        Boolean delete = articleService.deleteImage(filename);
        return CommonType.success(delete, "删除成功");
    }

    // 添加文章的接口
    @ResponseBody
    @PostMapping("/addArticle")
    public CommonType<ArticleVo> addArticle(@RequestParam("title") String title, @RequestParam("classify") String classify,
                                            @RequestParam("content") String content, @RequestParam("username") String username) {
        // 先判断用户是否存在
        Boolean userExist = userFeign.userExist(username).getData();
        if(userExist == null) {
            return CommonType.fail(ErrorEnum.USER_NOT_EXIST);
        }
        AddArticleRequest request = AddArticleRequest.of(title, classify, content, username);
        ArticleDto articleDto = articleService.addArticle(request);
        return CommonType.success(ArticleVo.of(articleDto), "文章发布成功");
    }

    @ResponseBody
    @PostMapping("/deleteArticle")
    public CommonType<ArticleVo> deleteArticle(@RequestParam("id") String id, @RequestParam("username") String username) {
        // 先判断用户是否存在
        Boolean userExist = userFeign.userExist(username).getData();
        if(userExist == null) {
            return CommonType.fail(ErrorEnum.USER_NOT_EXIST);
        }
        DeleteArticleRequest request = DeleteArticleRequest.of(id, username);
        ArticleDto articleDto = articleService.deleteArticle(request);
        return CommonType.success(ArticleVo.of(articleDto), "删除成功");
    }

    @ResponseBody
    @GetMapping("getArticleListByUsername")
    public CommonType<Page<Article>> getArticleListByUsername(@RequestParam("username") String username,
                                                                @RequestParam(name = "page", required = false) Integer page,
                                                                @RequestParam(name = "pageSize", required = false) Integer pageSize) {
        // 先判断用户是否存在
        Boolean userExist = userFeign.userExist(username).getData();
        if(userExist == null) {
            return CommonType.fail(ErrorEnum.USER_NOT_EXIST);
        }
        GetUserArticleRequest request = GetUserArticleRequest.of(username, page, pageSize);
        Page<Article> userArticle = articleService.getUserArticle(request);
        return CommonType.success(userArticle, "查询成功");
    }

}
