package com.jiac.article.service;

import com.jiac.article.request.*;
import com.jiac.common.dto.ArticleDto;
import com.jiac.common.entity.Article;
import com.jiac.common.vo.PageVo;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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

    PageVo<ArticleDto> getUserArticle(GetUserArticleRequest request);

    PageVo<ArticleDto> searchArticle(SearchArticleRequest request);

    ArticleDto getArticleMessageById(String articleId, String username);

    Boolean like(String username, String articleId);

    Boolean unlike(String username, String articleId);

    PageVo<ArticleDto> getLikeListByUser(GetLikeListRequest request);

    Integer countArticleByUser(String username);

    Integer countLikeByUser(String username);
}
