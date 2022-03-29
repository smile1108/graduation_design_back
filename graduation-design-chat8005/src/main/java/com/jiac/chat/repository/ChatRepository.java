package com.jiac.chat.repository;

import com.jiac.common.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * FileName: ChatRepository
 * Author: Jiac
 * Date: 2022/3/29 11:53
 */
@Repository
public interface ChatRepository extends JpaRepository<ChatMessage, String> {
}
