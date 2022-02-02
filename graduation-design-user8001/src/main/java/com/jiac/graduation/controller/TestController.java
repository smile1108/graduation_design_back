package com.jiac.graduation.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * FileName: TestController
 * Author: Jiac
 * Date: 2022/2/2 12:07
 */
@RestController
public class TestController {

    @ResponseBody
    @GetMapping("/test")
    public String test() {
        return "Test";
    }
}
