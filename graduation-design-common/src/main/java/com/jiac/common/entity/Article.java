package com.jiac.common.entity;

import com.jiac.common.utils.ArticleClassify;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * FileName: Article
 * Author: Jiac
 * Date: 2022/2/13 9:04
 */
@Entity
@Data
@Table(name = "article")
public class Article {
    @Id
    @Column(name = "id", length = 10)
    private String id;

    @Column(name = "title", length = 20)
    private String title;

    @Lob
    @Basic(fetch= FetchType.LAZY)
    @Column(name = "content", columnDefinition = "LONGTEXT")
    private String content;

    @Column(name = "publish_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date publishDate;

    @Column(name = "classify", length = 20)
    private String classify;

    @OneToOne
    @JoinColumn(name = "username")
    private User user;
}
