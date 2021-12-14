package com.server.Dotori.model.member.service.email;

import com.google.common.base.Verify;
import com.server.Dotori.model.member.dto.SenderDto;
import com.server.Dotori.util.EmailSender;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
public class AwsServiceTest {

    @Autowired
    private EmailSender sesEmailSender;

    @Test
    void 메일_발송_테스트(){
        String to = "s20018@gsm.hs.kr";
        String key = "123456";

        sesEmailSender.send(to,key);
    }
}
