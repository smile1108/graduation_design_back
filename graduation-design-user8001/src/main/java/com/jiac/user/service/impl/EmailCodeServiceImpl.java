package com.jiac.user.service.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.jiac.common.entity.EmailCode;
import com.jiac.user.repository.EmailCodeRepository;
import com.jiac.user.service.EmailCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * FileName: EmailCodeServiceImpl
 * Author: Jiac
 * Date: 2022/3/26 9:51
 */
@Service
public class EmailCodeServiceImpl implements EmailCodeService {

    @Autowired
    private EmailCodeRepository emailCodeRepository;

    @Override
    public void updateCode(String email, String code) {
        EmailCode emailCode = emailCodeRepository.findEmailCodeByEmail(email);
        if(emailCode == null) {
            // 表示现在这个邮箱还没有发送过邮件
            emailCode = new EmailCode();
        }
        emailCode.setEmail(email);
        emailCode.setCode(code);
        // 获取cookie过期时间
        DateTime now = DateUtil.date();
        // cookie过期时间为5分钟 要和响应给前端的过期时间一致
        DateTime offset = now.offset(DateField.MINUTE, 3);
        long expireTimestamp = offset.toTimestamp().getTime();
        emailCode.setExpireTimestamp(expireTimestamp);
        emailCodeRepository.save(emailCode);
    }
}
