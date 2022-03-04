package com.jiac.article.repository;

import com.jiac.common.entity.ArticleLike;
import com.jiac.common.entity.ArticleLikeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * FileName: ArticleLikeRepository
 * Author: Jiac
 * Date: 2022/3/2 12:28
 */
@Repository
public interface ArticleLikeRepository extends JpaRepository<ArticleLike, ArticleLikeKey>, JpaSpecificationExecutor {

    @Query(value = "select * from article_like where article_id = ?1 and username = ?2", nativeQuery = true)
    ArticleLike findByIdAndUsername(String id, String username);

    @Query(value = "select count(*) from article_like where article_id = ?1", nativeQuery = true)
    Integer getLikeCountByArticleId(String articleId);

    @Query(value = "select count(*) from article_like where username = ?1", nativeQuery = true)
    Integer getLikeCountByUsername(String username);
}
