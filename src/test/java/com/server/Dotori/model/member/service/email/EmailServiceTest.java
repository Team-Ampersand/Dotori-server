package com.server.Dotori.model.member.service.email;

import com.server.Dotori.model.member.Member;
import com.server.Dotori.model.member.dto.EmailDto;
import com.server.Dotori.model.member.dto.MemberDto;
import com.server.Dotori.model.member.repository.MemberRepository;
import com.server.Dotori.model.member.service.MemberService;
import com.server.Dotori.model.member.service.email.EmailService;
import com.server.Dotori.util.redis.RedisUtil;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void authKey(){
        //given
        MemberDto memberDto = MemberDto.builder()
                .username("노경준")
                .stdNum("2206")
                .password("1234")
                .email("s20018@gsm.hs.kr")
                .key("ABC1")
                .answer("hello")
                .build();
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));

        EmailDto emailDto = new EmailDto();
        emailDto.setUserEmail(memberDto.getEmail());

        //when
        String key = emailService.authKey(emailDto);

        //then
        System.out.println("======================== " + key + " =========================");
        assertThat(memberDto.getEmail()).isEqualTo(redisUtil.getData(key));
    }

    @Test
    void authPassword(){
        // given
        MemberDto memberDto = MemberDto.builder()
                .username("노경준")
                .stdNum("2206")
                .password("1234")
                .email("s20018@gsm.hs.kr")
                .key("ABC1")
                .answer("hello")
                .build();

        EmailDto emailDto = new EmailDto();
        emailDto.setUserEmail(memberDto.getEmail());

        // when
        memberService.signup(memberDto);
        Member result = emailService.authPassword(emailDto);

        // then
        assertThat(result.getPassword()).isEqualTo(memberRepository.findByEmail(memberDto.getEmail()).getPassword());
    }

}
