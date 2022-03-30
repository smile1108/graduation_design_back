package com.jiac.chat.repository;

import com.jiac.common.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * FileName: ChatRepository
 * Author: Jiac
 * Date: 2022/3/29 11:53
 */
@Repository
public interface ChatRepository extends JpaRepository<ChatMessage, String> {

    @Query(value = "select distinct to_user from chat_message where from_user = ?1" +
            " union " +
            "select distinct from_user from chat_message where to_user = ?1", nativeQuery = true)
    List<String> getChatList(String username);
}
