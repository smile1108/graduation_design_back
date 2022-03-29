package com.jiac.common.dto;

import com.jiac.common.entity.ChatMessage;
import com.jiac.common.entity.User;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.util.Date;

/**
 * FileName: ChatMessageDto
 * Author: Jiac
 * Date: 2022/3/29 11:45
 */
@Data
public class ChatMessageDto {
    private String id;

    private User fromUser;

    private User toUser;

    private String type;

    private String content;

    private Boolean haveRead;

    private Date publishDate;

    public static ChatMessageDto of(ChatMessage chatMessage) {
        ChatMessageDto chatMessageDto = new ChatMessageDto();
        BeanUtils.copyProperties(chatMessage, chatMessageDto);
        return chatMessageDto;
    }
}
