package com.jiac.chat.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;
import com.jiac.chat.feign.UserFeign;
import com.jiac.chat.repository.ChatRepository;
import com.jiac.chat.request.AddChatMessageRequest;
import com.jiac.chat.service.ChatService;
import com.jiac.common.dto.ChatMessageDto;
import com.jiac.common.dto.UserDto;
import com.jiac.common.entity.ChatMessage;
import com.jiac.common.entity.User;
import com.jiac.common.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * FileName: ChatServiceImpl
 * Author: Jiac
 * Date: 2022/3/29 11:53
 */
@Service
public class ChatServiceImpl implements ChatService {

    // 服务器上的图片的固定前缀
    @Value("${static-profile-url}")
    private String IMAGE_PREFIX;

    // nginx 存储图片的路径
    @Value("${nginx-static-dir}")
    private String NGINX_STATIC_DIR;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserFeign userFeign;

    @Override
    public String uploadImage(MultipartFile file) throws IOException {
        // 先放入静态目录中
        RandomUtil randomUtil = new RandomUtil();
        // 获取原来文件的类型 比如 jpg  png
        String[] split = file.getOriginalFilename().split("\\.");
        String suffix = split.length == 1 ? "jpg" : split[1];
        String randomFileName = randomUtil.randomString(20) + "." + suffix;
        // 根据随机生成的10位字符串 拼接上Nginx的静态目录 组成文件存储的路径
        String filePath = NGINX_STATIC_DIR + randomFileName;
        // 将文件写入对应路径中
        FileUtil.writeBytes(file.getBytes(), filePath);
        // 然后返回给前端 访问图片的路径
        return IMAGE_PREFIX + randomFileName;
    }

    @Override
    public List<UserVo> getChatList(String username) {
        List<String> chatUsernameList = chatRepository.getChatList(username);
        List<UserVo> userVoList = chatUsernameList.stream().map(chatUsername -> userFeign.getUserByUsername(chatUsername).getData()).collect(Collectors.toList());
        return userVoList;
    }

    @Override
    public ChatMessageDto addChatMessage(AddChatMessageRequest request) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setId(RandomUtil.randomString(10));
        User fromUser = new User();
        fromUser.setUsername(request.getFromUser());
        chatMessage.setFromUser(fromUser);
        User toUser = new User();
        toUser.setUsername(request.getToUser());
        chatMessage.setToUser(toUser);
        chatMessage.setType(request.getType());
        chatMessage.setContent(request.getContent());
        chatMessage.setHaveRead(false);
        chatMessage.setPublishDate(new Date());
        ChatMessage save = chatRepository.save(chatMessage);
        return ChatMessageDto.of(save);
    }
}
