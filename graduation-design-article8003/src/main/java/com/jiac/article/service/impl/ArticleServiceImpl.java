package com.jiac.article.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;
import com.jiac.article.repository.ArticleRepository;
import com.jiac.article.request.AddArticleRequest;
import com.jiac.article.service.ArticleService;
import com.jiac.common.dto.ArticleDto;
import com.jiac.common.entity.Article;
import com.jiac.common.entity.User;
import com.jiac.common.utils.ErrorEnum;
import com.jiac.common.utils.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

/**
 * FileName: ArticleServiceImpl
 * Author: Jiac
 * Date: 2022/2/12 7:22
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    // 服务器上的图片的固定前缀
    private final String IMAGE_PREFIX = "http://localhost/images/";

    // nginx 存储图片的路径
    private final String NGINX_STATIC_DIR = "E:\\nginx-1.20.2\\html\\images\\";

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public String uploadImage(MultipartFile file) throws IOException {
        // 先放入静态目录中
        RandomUtil randomUtil = new RandomUtil();
        // 获取原来文件的类型 比如 jpg  png
        String[] split = file.getOriginalFilename().split("\\.");
        String suffix = split.length == 1 ? "jpg" : split[1];
        String randomFileName = randomUtil.randomString(20) + "." + suffix;
        // 根据随机生成的10位字符串 拼接上Nginx的静态目录 组成文件存储的路径
        String filePath = NGINX_STATIC_DIR + randomFileName;
        // 将文件写入对应路径中
        FileUtil.writeBytes(file.getBytes(), filePath);
        // 然后返回给前端 访问图片的路径
        return IMAGE_PREFIX + randomFileName;
    }

    @Override
    public Boolean deleteImage(String filename) {
        // 给回来的filename是前端访问图片的路径 如 http://localhost/images/xxxxxx.jpg
        // 首先把文件名字分离出来
        String[] split = filename.split("/");
        // 经过测试 前端传递过来的路径中多了,[object File] 所以我们还需要过滤一层
        // 数组的最后一个就是文件的名字
        String randomName = split[split.length - 1].split(",")[0];
        // 然后根据目录 拼接成在文件系统中的路径
        String path = NGINX_STATIC_DIR + randomName;
        // 先判断文件是否存在 如果存在再删除
        boolean exist = FileUtil.exist(path);
        if(!exist) {
            throw new MyException(ErrorEnum.FILE_NOT_EXIST);
        }
        // 然后使用hutool 中的FileUtil删除文件
        FileUtil.del(path);
        return true;
    }

    @Override
    public ArticleDto addArticle(AddArticleRequest request) {
        Article article = new Article();
        RandomUtil randomUtil = new RandomUtil();
        String randomId = randomUtil.randomString(10);
        article.setId(randomId);
        article.setTitle(request.getTitle());
        article.setContent(request.getContent());
        article.setClassify(request.getClassify());
        article.setPublishDate(new Date());
        User user = new User();
        user.setUsername(request.getUsername());
        article.setUser(user);
        Article save = articleRepository.save(article);
        return ArticleDto.of(save);
    }
}
