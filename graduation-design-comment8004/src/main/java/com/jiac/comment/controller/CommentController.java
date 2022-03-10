package com.jiac.comment.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * FileName: CommentController
 * Author: Jiac
 * Date: 2022/3/10 13:25
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @ResponseBody
    @GetMapping("/test")
    public String test() {
        return "Hello World";
    }
}
