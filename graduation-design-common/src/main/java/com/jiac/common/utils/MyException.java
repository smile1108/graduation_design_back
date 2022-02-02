package com.jiac.common.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * FileName: MyException
 * Author: Jiac
 * Date: 2022/2/2 15:23
 */
@Data
@AllArgsConstructor
public class MyException extends RuntimeException {
    private ErrorEnum errorEnum;
}
