package com.jiac.chat.repository;

import com.jiac.common.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * FileName: ChatRepository
 * Author: Jiac
 * Date: 2022/3/29 11:53
 */
@Repository
public interface ChatRepository extends JpaRepository<ChatMessage, String>, JpaSpecificationExecutor {

    @Query(value = "select distinct to_user from chat_message where from_user = ?1" +
            " union " +
            "select distinct from_user from chat_message where to_user = ?1", nativeQuery = true)
    List<String> getChatList(String username);

    @Query(value = "select count(*) from chat_message where from_user = ?1 and" +
            " to_user = ?2 and have_read = 0", nativeQuery = true)
    Integer countUnreadByFromUserAndToUser(String fromUser, String toUser);

    @Query(value = "select count(*) from chat_message where to_user = ?1 and have_read = 0", nativeQuery = true)
    Integer countAllUnread(String username);

    @Query(value = "select * from chat_message where from_user = ?1 and to_user = ?2 " +
            "and have_read = 0", nativeQuery = true)
    List<ChatMessage> getUnreadMessage(String fromUser, String toUser);
}
