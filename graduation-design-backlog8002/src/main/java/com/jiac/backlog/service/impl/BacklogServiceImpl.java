package com.jiac.backlog.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.jiac.backlog.dto.BacklogDto;
import com.jiac.backlog.repository.BacklogRepository;
import com.jiac.backlog.request.AddBacklogRequest;
import com.jiac.backlog.service.BacklogService;
import com.jiac.common.entity.Backlog;
import com.jiac.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * FileName: BacklogServiceImpl
 * Author: Jiac
 * Date: 2022/2/9 11:53
 */
@Service
public class BacklogServiceImpl implements BacklogService {

    @Autowired
    private BacklogRepository backlogRepository;

    @Override
    public BacklogDto addBacklog(AddBacklogRequest request) {
        Backlog backlog = new Backlog();
        RandomUtil randomUtil = new RandomUtil();
        String randomId = randomUtil.randomString(10);
        backlog.setId(randomId);
        backlog.setTitle(request.getTitle());
        backlog.setDone(false);
        User user = new User();
        user.setUsername(request.getUsername());
        backlog.setUser(user);
        Backlog save = backlogRepository.save(backlog);
        return BacklogDto.of(save);
    }
}
