package com.jiac.backlog.repository;

import com.jiac.common.entity.Backlog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * FileName: BacklogRepository
 * Author: Jiac
 * Date: 2022/2/9 11:54
 */
@Repository
public interface BacklogRepository extends JpaRepository<Backlog, String> {
    @Query(value = "select * from backlog where username = ?1", nativeQuery = true)
    List<Backlog> getAllBacklogs(String username);

    @Query(value = "select * from backlog where id = ?1", nativeQuery = true)
    Backlog getBacklogById(String id);
}
