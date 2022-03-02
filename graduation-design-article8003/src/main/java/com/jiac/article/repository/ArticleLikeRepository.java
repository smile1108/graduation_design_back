package com.jiac.article.repository;

import com.jiac.common.entity.ArticleLike;
import com.jiac.common.entity.ArticleLikeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * FileName: ArticleLikeRepository
 * Author: Jiac
 * Date: 2022/3/2 12:28
 */
@Repository
public interface ArticleLikeRepository extends JpaRepository<ArticleLike, ArticleLikeKey> {

    @Query(value = "select * from article_like where id = ?1 and username = ?2", nativeQuery = true)
    ArticleLike findByIdAndUsername(String id, String username);
}
