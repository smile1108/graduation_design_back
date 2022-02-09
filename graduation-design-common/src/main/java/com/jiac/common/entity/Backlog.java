package com.jiac.common.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * FileName: Backlog
 * Author: Jiac
 * Date: 2022/2/9 11:15
 */
@Entity
@Data
@Table(name = "backlog")
public class Backlog {
    @Id
    @Column(name = "id", length = 10)
    private String id;

    @Column(name = "title", length = 20)
    private String title;

    @Column(name = "done")
    private Boolean done;

    @OneToOne
    @JoinColumn(name = "username")
    private User user;
}
