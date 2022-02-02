package com.jiac.common.utils;

/**
 * FileName: ErrorEnum
 * Author: Jiac
 * Date: 2022/2/2 14:00
 */
public enum ErrorEnum {
    USERNAME_EMPTY(500, "用户名不能为空"),
    PASSWORD_EMPTY(501, "密码不能为空"),
    USER_NOT_EXIST(502, "该用户不存在"),
    PASSWORD_WRONG(503, "用户名或密码不正确");


    private Integer code;
    private String msg;

    ErrorEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }
}
