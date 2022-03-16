package com.jiac.comment.repository;

import com.jiac.common.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * FileName: AnswerRepository
 * Author: Jiac
 * Date: 2022/3/16 9:51
 */
@Repository
public interface AnswerRepository extends JpaRepository<Answer, String>, JpaSpecificationExecutor {
}
