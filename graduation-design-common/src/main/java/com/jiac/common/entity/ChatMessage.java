package com.jiac.common.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * FileName: ChatMessage
 * Author: Jiac
 * Date: 2022/3/29 11:41
 */
@Entity
@Data
@Table(name = "chat_message")
public class ChatMessage {
    @Id
    @Column(name = "id", length = 10)
    private String id;

    @OneToOne
    @JoinColumn(name = "from_user")
    private User fromUser;

    @OneToOne
    @JoinColumn(name = "to_user")
    private User toUser;

    @Column(name = "type", length = 10)
    private String type;

    @Column(name = "content", length = 300)
    private String content;

    @Column(name = "have_read")
    private Boolean haveRead;

    @Column(name = "publish_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date publishDate;
}
