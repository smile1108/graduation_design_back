package com.jiac.article.repository;

import com.jiac.common.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * FileName: ArticleRepository
 * Author: Jiac
 * Date: 2022/2/13 9:43
 */
@Repository
public interface ArticleRepository extends JpaRepository<Article, String> {
}
