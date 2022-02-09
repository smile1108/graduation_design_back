package com.jiac.backlog.repository;

import com.jiac.common.entity.Backlog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * FileName: BacklogRepository
 * Author: Jiac
 * Date: 2022/2/9 11:54
 */
@Repository
public interface BacklogRepository extends JpaRepository<Backlog, String> {
}
