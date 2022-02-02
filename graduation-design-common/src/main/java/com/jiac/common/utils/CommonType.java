package com.jiac.common.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * FileName: CommonType
 * Author: Jiac
 * Date: 2022/2/2 13:44
 */
@Data
@AllArgsConstructor
public class CommonType<T> {
    private Integer code;
    private T data;
    private String msg;

    public static <T> CommonType<T> success(T data, String msg) {
        return new CommonType<>(200, data, msg);
    }

    public static <T> CommonType<T> fail(ErrorEnum error) {
        return new CommonType<>(error.getCode(), null, error.getMsg());
    }
}
