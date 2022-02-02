package com.jiac.graduation.config;

import com.jiac.common.utils.CommonType;
import com.jiac.common.utils.MyException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * FileName: MyExceptionHandler
 * Author: Jiac
 * Date: 2022/2/2 15:22
 */
@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(value = MyException.class)
    @ResponseBody
    public CommonType exceptionHandler(MyException ex) {
        return CommonType.fail(ex.getErrorEnum());
    }
}
