package com.jiac.user.service.impl;

import com.jiac.user.service.EmailServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * FileName: EmailServerImpl
 * Author: Jiac
 * Date: 2022/3/26 9:08
 */
@Service
public class EmailServerImpl implements EmailServer {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.from}")
    private String from;

    @Async("taskExecutor")
    @Override
    public void sendEmail(String subject, String to, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(subject);
        message.setFrom(from);
        message.setTo(to);
        message.setSentDate(new Date());
        message.setText(text);
        javaMailSender.send(message);
    }
}
