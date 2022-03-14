package com.jiac.common.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * FileName: Question
 * Author: Jiac
 * Date: 2022/3/13 11:11
 */
@Entity
@Data
@Table(name = "question")
public class Question {
    @Id
    @Column(name = "id", length = 10)
    private String id;

    @Column(name = "title", length = 50)
    private String title;

    @Lob
    @Basic(fetch= FetchType.LAZY)
    @Column(name = "content", columnDefinition = "LONGTEXT")
    private String content;

    @Column(name = "publish_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date publishDate;

    @OneToOne
    @JoinColumn(name = "username")
    private User user;
}
