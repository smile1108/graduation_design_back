package com.jiac.common.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * FileName: UserFollow
 * Author: Jiac
 * Date: 2022/3/5 9:58
 */
@Entity
@Data
@Table(name = "user_follow")
@IdClass(UserFollowKey.class)
public class UserFollow {

    @Id
    @Column(name = "username")
    private String username;

    @Id
    @Column(name = "follow_username")
    private String followUsername;

    // 用户关注的时间
    @Column(name = "follow_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date followDate;
}
