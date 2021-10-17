package com.server.Dotori.model.member.service.email;

import com.server.Dotori.model.member.Member;
import com.server.Dotori.model.member.dto.AuthPasswordDto;
import com.server.Dotori.model.member.dto.EmailDto;
import com.server.Dotori.model.member.dto.MemberDto;
import com.server.Dotori.model.member.enumType.Role;
import com.server.Dotori.model.member.repository.MemberRepository;
import com.server.Dotori.model.member.service.MemberService;
import com.server.Dotori.model.member.service.email.EmailService;
import com.server.Dotori.util.CurrentUserUtil;
import com.server.Dotori.util.redis.RedisUtil;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Disabled
    @Test
    void authKey(){
        //given
        MemberDto memberDto = MemberDto.builder()
                .username("노경준")
                .stdNum("2206")
                .password("1234")
                .email("s20018@gsm.hs.kr")
                .build();
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));

        EmailDto emailDto = new EmailDto();
        emailDto.setEmail(memberDto.getEmail());

        //when
        String key = emailService.authKey(emailDto);

        //then
        System.out.println("======================== " + key + " =========================");
        assertThat(key).isEqualTo(redisUtil.getData(key));
    }


}
