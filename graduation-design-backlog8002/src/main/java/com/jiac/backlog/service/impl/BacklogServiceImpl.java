package com.jiac.backlog.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.jiac.backlog.dto.BacklogDto;
import com.jiac.backlog.repository.BacklogRepository;
import com.jiac.backlog.request.*;
import com.jiac.backlog.service.BacklogService;
import com.jiac.common.entity.Backlog;
import com.jiac.common.entity.User;
import com.jiac.common.utils.ErrorEnum;
import com.jiac.common.utils.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    public List<BacklogDto> getAllBacklogs(String username) {
        List<Backlog> backlogList = backlogRepository.getAllBacklogs(username);
        List<BacklogDto> backlogDtos = backlogList.stream().map(b -> BacklogDto.of(b)).collect(Collectors.toList());
        return backlogDtos;
    }

    @Override
    public BacklogDto done(BacklogDoneRequest request) {
        // 先根据id查找 看看该待办事项是否存在
        Backlog backlog = backlogRepository.getBacklogById(request.getId());
        if(backlog == null) {
            throw new MyException(ErrorEnum.BACKLOG_NOT_EXIST);
        }
        if(backlog.getDone()) {
            throw new MyException(ErrorEnum.DO_NOT_DONE_AGAIN);
        }
        if(!backlog.getUser().getUsername().equals(request.getUsername())) {
            throw new MyException(ErrorEnum.NO_PERMISSION);
        }
        // 所有验证都通过之后 再进行修改
        backlog.setDone(true);
        Backlog save = backlogRepository.save(backlog);
        return BacklogDto.of(save);
    }

    @Override
    public BacklogDto undone(BacklogUndoneRequest request) {
        // 先根据id查找 看看该待办事项是否存在
        Backlog backlog = backlogRepository.getBacklogById(request.getId());
        if(backlog == null) {
            throw new MyException(ErrorEnum.BACKLOG_NOT_EXIST);
        }
        if(!backlog.getDone()) {
            throw new MyException(ErrorEnum.DO_NOT_DONE_AGAIN);
        }
        if(!backlog.getUser().getUsername().equals(request.getUsername())) {
            throw new MyException(ErrorEnum.NO_PERMISSION);
        }
        // 所有验证都通过之后 再进行修改
        backlog.setDone(false);
        Backlog save = backlogRepository.save(backlog);
        return BacklogDto.of(save);
    }

    @Override
    public BacklogDto deleteBacklog(BacklogDeleteRequest request) {
        // 先根据id查找 看看该待办事项是否存在
        Backlog backlog = backlogRepository.getBacklogById(request.getId());
        if(backlog == null) {
            throw new MyException(ErrorEnum.BACKLOG_NOT_EXIST);
        }
        if(!backlog.getUser().getUsername().equals(request.getUsername())) {
            throw new MyException(ErrorEnum.NO_PERMISSION);
        }
        // 都通过之后 再进行删除
        backlogRepository.delete(backlog);
        return BacklogDto.of(backlog);
    }

    @Override
    public Boolean checkAllOrNone(BacklogCheckAllOrNoneRequest request) {
        List<Backlog> allBacklogs = backlogRepository.getAllBacklogs(request.getUsername());
        for(Backlog backlog : allBacklogs) {
            backlog.setDone(request.getDone());
            // 保存进数据库
            backlogRepository.save(backlog);
        }
        return true;
    }
}
