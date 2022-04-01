package com.jiac.chat.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;
import com.jiac.chat.feign.UserFeign;
import com.jiac.chat.repository.ChatRepository;
import com.jiac.chat.request.AddChatMessageRequest;
import com.jiac.chat.request.GetChatMessageListRequest;
import com.jiac.chat.service.ChatService;
import com.jiac.common.dto.ChatMessageDto;
import com.jiac.common.dto.UserDto;
import com.jiac.common.entity.Article;
import com.jiac.common.entity.ChatMessage;
import com.jiac.common.entity.Comment;
import com.jiac.common.entity.User;
import com.jiac.common.vo.PageVo;
import com.jiac.common.vo.UserChatVo;
import com.jiac.common.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
    public List<UserChatVo> getChatList(String username) {
        List<String> chatUsernameList = chatRepository.getChatList(username);
        List<UserChatVo> userChatVoList = chatUsernameList.stream().map(chatUsername -> {
            UserVo userVo = userFeign.getUserByUsername(chatUsername).getData();
            UserChatVo userChatVo = UserChatVo.of(userVo);
            userChatVo.setUnreadCount(chatRepository.countUnreadByFromUserAndToUser(chatUsername, username));
            return userChatVo;
        }).collect(Collectors.toList());
        return userChatVoList;
    }

    @Override
    public UserChatVo getUserChatMessage(String username) {
        UserVo userVo = userFeign.getUserByUsername(username).getData();
        UserChatVo userChatVo = UserChatVo.of(userVo);
        userChatVo.setUnreadCount(0);
        return userChatVo;
    }

    @Override
    public PageVo<ChatMessageDto> getChatMessageList(GetChatMessageListRequest request) {
        Sort sort = Sort.by(Sort.Direction.DESC, "publishDate");
        int page = 0;
        int pageSize = request.getNumber();
        PageRequest pageRequest = PageRequest.of(page, pageSize, sort);
        String username1 = request.getUsername1();
        User user1 = new User();
        user1.setUsername(username1);
        String username2 = request.getUsername2();
        User user2 = new User();
        user2.setUsername(username2);
        Specification<ChatMessage> specification = (Specification<ChatMessage>) (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            Predicate predicate = cb.or(cb.and(cb.equal(root.get("fromUser"), user1), cb.equal(root.get("toUser"), user2)),
                    cb.and(cb.equal(root.get("fromUser"), user2), cb.equal(root.get("toUser"), user1)));
            predicateList.add(predicate);
            Predicate[] predicates = new Predicate[predicateList.size()];
            return query.where(predicateList.toArray(predicates)).getRestriction();
        };
        Page<ChatMessage> chatMessagePage = chatRepository.findAll(specification, pageRequest);
        return transferChatMessagePage2ChatMessageDtoPageVo(chatMessagePage);
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

    private PageVo<ChatMessageDto> transferChatMessagePage2ChatMessageDtoPageVo(Page<ChatMessage> chatMessagePage) {
        List<ChatMessage> chatMessageList = new ArrayList<>();
        for(ChatMessage chatMessage : chatMessagePage.getContent()) {
            chatMessageList.add(chatMessage);
        }
        Collections.reverse(chatMessageList);
        List<ChatMessageDto> chatMessageDtoList = chatMessageList.stream().map(ChatMessageDto::of).collect(Collectors.toList());
        PageVo<ChatMessageDto> chatMessageDtoPageVo = new PageVo<>();
        chatMessageDtoPageVo.setLists(chatMessageDtoList);
        chatMessageDtoPageVo.setSumPage(chatMessagePage.getTotalPages());
        chatMessageDtoPageVo.setCount(chatMessagePage.getTotalElements());
        return chatMessageDtoPageVo;
    }
}
