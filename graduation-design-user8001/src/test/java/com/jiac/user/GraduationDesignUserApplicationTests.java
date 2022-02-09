package com.jiac.user;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GraduationDesignUserApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testDate() {
        DateTime now = DateUtil.date();
        System.out.println(now);
        System.out.println(now.toTimestamp().getTime());
        DateTime offset = now.offset(DateField.MINUTE, 5);
        System.out.println(offset);
        System.out.println(offset.toTimestamp().getTime());
    }

}
