package com.jiac.article.repository;

import com.jiac.common.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * FileName: QuestionRepository
 * Author: Jiac
 * Date: 2022/3/13 11:23
 */
@Repository
public interface QuestionRepository extends JpaRepository<Question, String>, JpaSpecificationExecutor {
}
