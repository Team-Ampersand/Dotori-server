package com.server.Dotori.model.member.service;

import com.server.Dotori.model.member.dto.EmailDto;
import com.server.Dotori.model.member.dto.MemberEmailKeyDto;
import com.server.Dotori.util.redis.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @Test
    void authKey(){
        //given
        EmailDto emailDto = new EmailDto();

        emailDto.setUserEmail("hippo911911@naver.com");

        //when
        String result = emailService.authKey(emailDto);

        //then
        System.out.println("result [" + result +"]");
    }

}
