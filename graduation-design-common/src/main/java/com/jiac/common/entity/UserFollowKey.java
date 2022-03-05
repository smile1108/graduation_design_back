package com.jiac.common.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * FileName: UserFollowKey
 * Author: Jiac
 * Date: 2022/3/5 9:57
 */
@MappedSuperclass
public class UserFollowKey implements Serializable {

    @Id
    @Column(name = "username")
    private String username;

    @Id
    @Column(name = "follow_username")
    private String followUsername;
}
