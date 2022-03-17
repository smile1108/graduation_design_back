package com.jiac.comment.repository;

import com.jiac.common.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * FileName: CommentRepository
 * Author: Jiac
 * Date: 2022/3/10 13:49
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, String>, JpaSpecificationExecutor {
    @Query(value = "select count(*) from comment where username = ?1", nativeQuery = true)
    Integer countCommentByUsername(String username);

    @Query(value = "select count(*) from comment where article_id = ?1", nativeQuery = true)
    Integer countCommentByArticleId(String articleId);

    @Transactional
    @Modifying
    @Query(value = "delete from comment where article_id = ?1", nativeQuery = true)
    void deleteCommentByArticleId(String articleId);
}
