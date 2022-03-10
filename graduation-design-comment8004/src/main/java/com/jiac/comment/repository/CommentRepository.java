package com.jiac.comment.repository;

import com.jiac.common.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * FileName: CommentRepository
 * Author: Jiac
 * Date: 2022/3/10 13:49
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {
}
