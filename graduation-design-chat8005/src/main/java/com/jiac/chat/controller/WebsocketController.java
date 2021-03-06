package com.jiac.chat.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.jiac.chat.request.AddChatMessageRequest;
import com.jiac.chat.service.ChatService;
import com.jiac.common.dto.ChatMessageDto;
import com.jiac.common.dto.FrontMessageDto;
import com.jiac.common.vo.ChatMessageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * FileName: WebsocketController
 * Author: Jiac
 * Date: 2022/3/29 11:19
 */
@ServerEndpoint(value = "/chat/{username}")
@Controller
public class WebsocketController {

    private static ChatService chatService;

    @Autowired
    public void setChatService(ChatService chatService) {
        WebsocketController.chatService = chatService;
    }

    /**
     * 存放所有在线的客户端
     */
    private static Map<String, Session> clients = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        //将新用户存入在线的组
        clients.put(username, session);
        System.out.println(clients);
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
        System.out.println("session close. ID:" + session.getId());
    }

    /**
     * 收到客户端消息后调用的方法
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        // 将前端传递过来的消息JSON字符串转换成对象
        FrontMessageDto frontMessageDto = objectMapper.readValue(message, FrontMessageDto.class);
        AddChatMessageRequest request = AddChatMessageRequest.of(frontMessageDto.getFrom(), frontMessageDto.getTo(), frontMessageDto.getType(), frontMessageDto.getContent());
        // 操作数据库 聊天消息表中添加记录
        ChatMessageDto chatMessageDto = chatService.addChatMessage(request);
        ChatMessageVo chatMessageVo = ChatMessageVo.of(chatMessageDto);
        JsonMapper jsonMapper = new JsonMapper();
        // 通过JSON字符串中的to字段获取需要发送给哪个客户端
        Session session1 = clients.get(frontMessageDto.getTo());
        if(session1 != null) {
            // 通过session向对应的客户端推送消息 通过JSON工具将刚才添加的消息对象转换成JSON字符串
            session1.getBasicRemote().sendText(jsonMapper.writeValueAsString(chatMessageVo));
        }
        session.getBasicRemote().sendText(jsonMapper.writeValueAsString(chatMessageVo));
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }
}
