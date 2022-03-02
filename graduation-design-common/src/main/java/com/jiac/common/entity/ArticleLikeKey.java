package com.jiac.common.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * FileName: ArticleLikeKey
 * Author: Jiac
 * Date: 2022/3/2 12:40
 */
@MappedSuperclass
public class ArticleLikeKey implements Serializable {

    @Id
    @Column(name = "article_id")
    private String articleId;

    @Id
    @Column(name = "username")
    private String username;
}
