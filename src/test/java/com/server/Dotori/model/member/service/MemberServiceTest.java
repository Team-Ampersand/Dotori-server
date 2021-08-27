package com.server.Dotori.model.member.service;

import com.server.Dotori.model.member.dto.MemberDto;
import com.server.Dotori.model.member.enumType.Role;
import com.server.Dotori.model.member.repository.MemberRepository;
import com.server.Dotori.util.CurrentUserUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class MemberServiceTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void signup(){
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

        //when
        memberService.signup(memberDto);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                memberDto.getUsername(),
                memberDto.getPassword(),
                List.of(new SimpleGrantedAuthority(Role.ROLE_ADMIN.name())));
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(token);
        System.out.println("=================================");
        System.out.println(context);

        //then
        String currentUserName = CurrentUserUtil.getCurrentUserNickname();
        assertThat(currentUserName).isEqualTo(memberRepository.findByUsername("노경준").getUsername());
    }

}
