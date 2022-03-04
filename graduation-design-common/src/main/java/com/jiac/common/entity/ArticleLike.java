package com.jiac.common.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * FileName: ArticleLike
 * Author: Jiac
 * Date: 2022/3/2 12:26
 */
@Entity
@Data
@Table(name = "article_like")
@IdClass(ArticleLikeKey.class)
public class ArticleLike extends ArticleLikeKey{

    @Id
    @Column(name = "article_id")
    private String articleId;

    @Id
    @Column(name = "username")
    private String username;

    // 作者添加喜欢的时间
    @Column(name = "like_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date likeDate;
}
