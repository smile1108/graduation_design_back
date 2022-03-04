package com.jiac.article.repository;

import com.jiac.common.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * FileName: ArticleRepository
 * Author: Jiac
 * Date: 2022/2/13 9:43
 */
@Repository
public interface ArticleRepository extends JpaRepository<Article, String>, JpaSpecificationExecutor {
    @Query(value = "select count(*) from article where username = ?1", nativeQuery = true)
    Integer countArticle(String username);
}
