package com.jiac.common.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * FileName: EmailCode
 * Author: Jiac
 * Date: 2022/3/26 9:47
 */
@Entity
@Data
@Table(name = "email_code")
public class EmailCode {

    @Id
    @Column(name = "email", length = 64)
    private String email;

    @Column(name = "code", length = 6)
    private String code;

    @Column(name = "expireTimestamp")
    private Long expireTimestamp;
}
