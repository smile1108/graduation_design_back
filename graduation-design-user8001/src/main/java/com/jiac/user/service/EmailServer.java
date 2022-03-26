package com.jiac.user.service;

/**
 * FileName: EmailServer
 * Author: Jiac
 * Date: 2022/3/26 9:07
 */
public interface EmailServer {
    void  sendEmail(String subject, String to, String text);
}
