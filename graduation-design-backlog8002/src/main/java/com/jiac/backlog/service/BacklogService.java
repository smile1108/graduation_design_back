package com.jiac.backlog.service;

import com.jiac.backlog.dto.BacklogDto;
import com.jiac.backlog.request.AddBacklogRequest;

import java.util.List;

/**
 * FileName: BacklogService
 * Author: Jiac
 * Date: 2022/2/9 11:52
 */
public interface BacklogService {
    BacklogDto addBacklog(AddBacklogRequest request);

    List<BacklogDto> getAllBacklogs(String username);
}
