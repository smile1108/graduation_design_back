package com.jiac.common.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * FileName: Comment
 * Author: Jiac
 * Date: 2022/3/10 13:28
 */
@Entity
@Data
@Table(name = "comment")
public class Comment {

    @Id
    @Column(name = "id", length = 10)
    private String id;

    @Column(name = "content", length = 300)
    private String content;

    @Column(name = "publish_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date publishDate;

    @OneToOne
    @JoinColumn(name = "username")
    private User user;

    @OneToOne
    @JoinColumn(name = "article_id")
    private Article article;
}
