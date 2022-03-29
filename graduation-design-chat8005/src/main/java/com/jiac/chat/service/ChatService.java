package com.jiac.chat.service;

import com.jiac.chat.request.AddChatMessageRequest;
import com.jiac.common.dto.ChatMessageDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * FileName: ChatService
 * Author: Jiac
 * Date: 2022/3/29 11:53
 */
public interface ChatService {

    String uploadImage(MultipartFile file) throws IOException;

    ChatMessageDto addChatMessage(AddChatMessageRequest request);
}
