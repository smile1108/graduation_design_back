package com.jiac.chat.service;

import com.jiac.chat.request.AddChatMessageRequest;
import com.jiac.chat.request.GetChatMessageListRequest;
import com.jiac.common.dto.ChatMessageDto;
import com.jiac.common.dto.UserDto;
import com.jiac.common.vo.PageVo;
import com.jiac.common.vo.UserChatVo;
import com.jiac.common.vo.UserVo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * FileName: ChatService
 * Author: Jiac
 * Date: 2022/3/29 11:53
 */
public interface ChatService {

    String uploadImage(MultipartFile file) throws IOException;

    List<UserChatVo> getChatList(String username);

    UserChatVo getUserChatMessage(String username);

    Integer countAllUnread(String username);

    Boolean clearUnreadCount(String fromUser, String toUser);

    PageVo<ChatMessageDto> getChatMessageList(GetChatMessageListRequest request);

    ChatMessageDto addChatMessage(AddChatMessageRequest request);
}
