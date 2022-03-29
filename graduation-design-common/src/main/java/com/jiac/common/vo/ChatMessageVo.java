package com.jiac.common.vo;

import com.jiac.common.dto.ChatMessageDto;
import com.jiac.common.entity.User;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * FileName: ChatMessageVo
 * Author: Jiac
 * Date: 2022/3/29 11:46
 */
@Data
public class ChatMessageVo {
    private String id;

    private User fromUser;

    private User toUser;

    private String type;

    private String content;

    private Boolean haveRead;

    private Date publishDate;

    public static ChatMessageVo of(ChatMessageDto chatMessageDto) {
        ChatMessageVo chatMessageVo = new ChatMessageVo();
        BeanUtils.copyProperties(chatMessageDto, chatMessageVo);
        return chatMessageVo;
    }
}
