package com.jiac.chat.controller;

import com.jiac.chat.service.ChatService;
import com.jiac.common.utils.CommonType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
}
