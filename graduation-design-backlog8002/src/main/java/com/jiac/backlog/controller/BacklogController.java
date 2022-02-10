package com.jiac.backlog.controller;

import com.jiac.backlog.dto.BacklogDto;
import com.jiac.backlog.feign.UserFeign;
import com.jiac.backlog.request.*;
import com.jiac.backlog.service.BacklogService;
import com.jiac.backlog.vo.BacklogVo;
import com.jiac.common.entity.Backlog;
import com.jiac.common.utils.CommonType;
import com.jiac.common.utils.ErrorEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * FileName: BacklogController
 * Author: Jiac
 * Date: 2022/2/9 11:12
 */
@RestController
@RequestMapping("/backlog")
public class BacklogController {

    @Autowired
    private BacklogService backlogService;

    @Autowired
    private UserFeign userFeign;

    // 添加待办事项的接口
    @ResponseBody
    @PostMapping("/addBacklog")
    public CommonType<BacklogVo> addBacklog(@RequestParam("title") String title,
                                            @RequestParam("username") String username) {
        AddBacklogRequest request = AddBacklogRequest.of(title, username);

        // 先判断该用户是否存在
        CommonType<Boolean> booleanCommonType = userFeign.userExist(username);
        Boolean userExist = booleanCommonType.getData();
        if(userExist == null) {
            return CommonType.fail(ErrorEnum.USER_NOT_EXIST);
        }
        // 通过service 添加待办事项
        BacklogDto backlogDto = backlogService.addBacklog(request);
        return CommonType.success(BacklogVo.of(backlogDto), "待办事项添加成功");
    }

    // 获取所有待办事项的接口
    @ResponseBody
    @GetMapping("/getAllBacklogs")
    public CommonType<List<BacklogVo>> getAllBacklogs(@RequestParam("username") String username) {
        // 先判断用户是否存在
        Boolean userExist = userFeign.userExist(username).getData();
        if(userExist == null) {
            return CommonType.fail(ErrorEnum.USER_NOT_EXIST);
        }
        List<BacklogDto> backlogDtos = backlogService.getAllBacklogs(username);
        List<BacklogVo> vos = backlogDtos.stream().map(dto -> BacklogVo.of(dto)).collect(Collectors.toList());
        return CommonType.success(vos, "查询成功");
    }

    // 完成待办事项的接口
    @ResponseBody
    @GetMapping("/done")
    public CommonType<BacklogVo> done(@RequestParam("id") String id, @RequestParam("username") String username) {
        // 先判断用户是否存在
        Boolean userExist = userFeign.userExist(username).getData();
        if(userExist == null) {
            return CommonType.fail(ErrorEnum.USER_NOT_EXIST);
        }
        BacklogDoneRequest request = BacklogDoneRequest.of(id, username);
        BacklogDto done = backlogService.done(request);
        return CommonType.success(BacklogVo.of(done), "修改成功");
    }

    @ResponseBody
    @GetMapping("/undone")
    public CommonType<BacklogVo> undone(@RequestParam("id") String id, @RequestParam("username") String username) {
        // 先判断用户是否存在
        Boolean userExist = userFeign.userExist(username).getData();
        if(userExist == null) {
            return CommonType.fail(ErrorEnum.USER_NOT_EXIST);
        }
        BacklogUndoneRequest request = BacklogUndoneRequest.of(id, username);
        BacklogDto undone = backlogService.undone(request);
        return CommonType.success(BacklogVo.of(undone), "修改成功");
    }

    @ResponseBody
    @PostMapping("/deleteBacklog")
    public CommonType<BacklogVo> deleteBacklog(@RequestParam("id") String id, @RequestParam("username") String username) {
        // 先判断用户是否存在
        Boolean userExist = userFeign.userExist(username).getData();
        if(userExist == null) {
            return CommonType.fail(ErrorEnum.USER_NOT_EXIST);
        }
        BacklogDeleteRequest request = BacklogDeleteRequest.of(id, username);
        BacklogDto backlogDto = backlogService.deleteBacklog(request);
        return CommonType.success(BacklogVo.of(backlogDto), "删除成功");
    }

    @ResponseBody
    @GetMapping("/checkAllOrNone")
    public CommonType<Boolean> checkAllOrNone(@RequestParam("done") Boolean done, @RequestParam("username") String username) {
        // 先判断用户是否存在
        Boolean userExist = userFeign.userExist(username).getData();
        if(userExist == null) {
            return CommonType.fail(ErrorEnum.USER_NOT_EXIST);
        }
        BacklogCheckAllOrNoneRequest request = BacklogCheckAllOrNoneRequest.of(done, username);
        Boolean success = backlogService.checkAllOrNone(request);
        return CommonType.success(success, "修改成功");
    }
}
