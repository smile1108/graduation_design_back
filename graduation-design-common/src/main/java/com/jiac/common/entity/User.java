package com.jiac.common.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * FileName: User
 * Author: Jiac
 * Date: 2022/2/2 13:16
 */
@Entity
@Data
@Table(name = "user")
public class User {

    @Id
    @Column(name = "username", length = 20)
    private String username;

    @Column(name = "password", length = 50)
    private String password;

    @Column(name = "email", length = 64)
    private String email;

    @Column(name = "nickname", length = 20)
    private String nickname;

    @Column(name = "school", length = 20)
    private String school;

    @Column(name = "college", length = 20)
    private String college;

    @Column(name = "specialty", length = 20)
    private String specialty;

    @Column(name = "gender", length = 10)
    private String gender;

    @Column(name = "resume", length = 50)
    private String resume;

    @Column(name = "profile", length = 50)
    private String profile;
}
