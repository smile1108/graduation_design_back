package com.jiac.article.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * FileName: ArticleService
 * Author: Jiac
 * Date: 2022/2/12 7:22
 */
public interface ArticleService {
    String uploadImage(MultipartFile file) throws IOException;
}
