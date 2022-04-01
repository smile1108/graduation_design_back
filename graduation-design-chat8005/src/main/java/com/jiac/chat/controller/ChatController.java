package com.jiac.chat.controller;

import com.jiac.chat.request.GetChatMessageListRequest;
import com.jiac.chat.service.ChatService;
import com.jiac.common.dto.ChatMessageDto;
import com.jiac.common.utils.CommonType;
import com.jiac.common.vo.ChatMessageVo;
import com.jiac.common.vo.PageVo;
import com.jiac.common.vo.UserChatVo;
import com.jiac.common.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * FileName: ChatController
 * Author: Jiac
 * Date: 2022/3/29 11:51
 */
@RestController
@RequestMapping(value = "/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @ResponseBody
    @PostMapping("/uploadImage")
    public CommonType<String> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        // 这个接口只需要存储对应的图片 并返回给前端一个在服务器上的图片的路径 不需要存入数据库
        String imagePath = chatService.uploadImage(file);
        // 然后直接返回给前端这个图片的访问路径
        return CommonType.success(imagePath, "上传成功");
    }

    @ResponseBody
    @GetMapping("/getChatList")
    public CommonType<List<UserChatVo>> getChatList(@RequestParam("username") String username) {
        List<UserChatVo> chatList = chatService.getChatList(username);
        return CommonType.success(chatList, "查询成功");
    }

    @ResponseBody
    @GetMapping("/getUserChatMessage")
    public CommonType<UserChatVo> getUserChatMessage(@RequestParam("username") String username) {
        UserChatVo userChatVo = chatService.getUserChatMessage(username);
        return CommonType.success(userChatVo, "查询成功");
    }

    @ResponseBody
    @GetMapping("/countAllUnread")
    public CommonType<Integer> countAllUnread(@RequestParam("username") String username) {
        return CommonType.success(chatService.countAllUnread(username), "查询成功");
    }

    @ResponseBody
    @GetMapping("/getChatMessageList")
    public CommonType<PageVo<ChatMessageVo>> getChatMessageList(@RequestParam("username1") String username1,
                                                                @RequestParam("username2") String username2,
                                                                @RequestParam(value = "number", required = false) Integer number) {
        GetChatMessageListRequest request = GetChatMessageListRequest.of(username1, username2, number);
        PageVo<ChatMessageDto> chatMessageDtoPageVo = chatService.getChatMessageList(request);
        return CommonType.success(transferChatMessageDtoPageVo2ChatMessageVoPageVo(chatMessageDtoPageVo), "查询成功");
    }

    private PageVo<ChatMessageVo> transferChatMessageDtoPageVo2ChatMessageVoPageVo(PageVo<ChatMessageDto> chatMessageDtoPageVo) {
        PageVo<ChatMessageVo> chatMessageVoPageVo = new PageVo<>();
        BeanUtils.copyProperties(chatMessageDtoPageVo, chatMessageVoPageVo);
        chatMessageVoPageVo.setLists(chatMessageDtoPageVo.getLists().stream().map(ChatMessageVo::of).collect(Collectors.toList()));
        return chatMessageVoPageVo;
    }
}
