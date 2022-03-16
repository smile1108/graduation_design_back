package com.jiac.common.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * FileName: Answer
 * Author: Jiac
 * Date: 2022/3/16 9:29
 */
@Data
@Entity
@Table(name = "answer")
public class Answer {

    @Id
    @Column(name = "id", length = 10)
    private String id;

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

    @OneToOne
    @JoinColumn(name = "question_id")
    private Question question;
}
