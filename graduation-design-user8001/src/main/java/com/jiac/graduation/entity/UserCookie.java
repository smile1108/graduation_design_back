package com.jiac.graduation.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * FileName: UserCookie
 * Author: Jiac
 * Date: 2022/2/7 16:28
 */
@Entity
@Data
@Table(name = "user_cookie")
public class UserCookie {

    @Id
    @Column(name = "cookie", length = 32)
    private String cookie;

    @OneToOne
    @JoinColumn(name = "username")
    private User user;
}
