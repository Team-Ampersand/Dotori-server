package com.server.Dotori.domain.member.email;

import com.server.Dotori.global.util.EmailSender;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class AwsServiceTest {

    @Autowired
    private EmailSender sesEmailSender;

    @Disabled
    @Test
    void 메일_발송_테스트(){
        String to = "s20018@gsm.hs.kr";
        String key = "123456";

        sesEmailSender.send(to,key);
    }
}
