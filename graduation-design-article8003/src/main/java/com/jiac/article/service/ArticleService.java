package com.jiac.article.service;

import com.jiac.article.request.AddArticleRequest;
import com.jiac.article.request.DeleteArticleRequest;
import com.jiac.common.dto.ArticleDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * FileName: ArticleService
 * Author: Jiac
 * Date: 2022/2/12 7:22
 */
public interface ArticleService {
    String uploadImage(MultipartFile file) throws IOException;

    Boolean deleteImage(String filename);

    ArticleDto addArticle(AddArticleRequest request);

    ArticleDto deleteArticle(DeleteArticleRequest request);
}
