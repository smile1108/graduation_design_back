package com.jiac.common.utils;

/**
 * FileName: ErrorEnum
 * Author: Jiac
 * Date: 2022/2/2 14:00
 */
public enum ErrorEnum {
    USERNAME_EMPTY(500, "用户名不能为空"),
    PASSWORD_EMPTY(501, "密码不能为空"),
    PASSWORD_WRONG(502, "用户名或密码不正确"),
    PASSWORD_NOT_FIT(503, "两次密码输入不一致"),
    USER_IS_EXIST(504, "该用户名已被注册"),
    USERNAME_LENGTH_NOT_FIT(505, "学号长度不能小于8位或者大于20位"),
    PASSWORD_PATTERN_WRONG(505, "密码必须至少包含一个大写字母、一个小写字母、一个数字和一个特殊字符(.@$!%*?&),长度在8-16之间"),
    USER_NOT_EXIST(506, "该用户不存在");


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
