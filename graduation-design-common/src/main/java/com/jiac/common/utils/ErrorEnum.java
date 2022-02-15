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
    USER_NOT_EXIST(506, "该用户不存在"),
    NICKNAME_TOO_LENGTH(507, "昵称长度不能超过20个字符"),
    SCHOOL_TOO_LENGTH(508, "学校长度不能超过20个字符"),
    COLLEGE_TOO_LENGTH(509, "学院长度不能超过20个字符"),
    SPECIALTY_TOO_LENGTH(510, "专业长度不能超过20个字符"),
    RESUME_TOO_LENGTH(511, "个人简介长度不能超过50个字符"),
    ILLEGAL_GENDER(512, "非法的性别"),
    ILLEGAL_COOKIE(513, "非法的cookie"),
    BACKLOG_TITLE_TOO_LENGTH(514, "待办事项内容不能超过20个字符"),
    ID_TOO_LENGTH(515, "待办事项ID非法"),
    BACKLOG_NOT_EXIST(516, "待办事项不存在"),
    DO_NOT_DONE_AGAIN(517, "请勿重复操作"),
    NO_PERMISSION(518, "没有权限"),
    USER_MESSAGE_EXPIRE(519, "用户身份认证过期, 请重新登录"),
    FILE_NOT_EXIST(520, "文件不存在"),
    ARTICLE_TITLE_TOO_LONG(521, "文章长度不能超过20个字符"),
    ARTICLE_CLASSIFY_TOO_LONG(522, "文章分类长度不能超过20个字符"),
    ILLEGAL_CLASSIFY(523, "非法的文章分类"),
    ARTICLE_NOT_EXIST(524, "文章不存在");


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
