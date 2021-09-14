package com.server.Dotori.model.member.service;

import com.server.Dotori.model.member.dto.MemberDto;
import com.server.Dotori.model.member.dto.MemberLoginDto;
import com.server.Dotori.model.member.dto.MemberPasswordDto;
import com.server.Dotori.model.member.enumType.Role;
import com.server.Dotori.model.member.repository.MemberRepository;
import com.server.Dotori.util.CurrentUserUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
@Transactional
public class MemberServiceTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Test
    void signup(){
        // given
        MemberDto memberDto = MemberDto.builder()
                .username("관리자")
                .stdNum("1111")
                .password("1234")
                .email("s20000@gsm.hs.kr")
                .answer("root")
                .build();
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));

        // when
        Long result = memberService.signup(memberDto);

        // then
        assertThat(result).isEqualTo(memberRepository.findByEmail(memberDto.getEmail()).getId());
    }

    @BeforeEach
    @DisplayName("로그인 되어있는 유저를 확인하는 테스트")
    void currentUser() {
        //given
        MemberDto memberDto = MemberDto.builder()
                .username("노경준")
                .stdNum("2206")
                .password("1234")
                .email("s20018@gsm.hs.kr")
                .answer("노갱")
                .build();
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));
        memberRepository.save(memberDto.toEntity());
        System.out.println("======== saved =========");

        // when login session 발급
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                memberDto.getUsername(),
                memberDto.getPassword(),
                List.of(new SimpleGrantedAuthority(Role.ROLE_ADMIN.name())));
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(token);
        System.out.println("=================================");
        System.out.println(context);

        //then
        String currentUsername = CurrentUserUtil.getCurrentUserNickname();
        assertEquals("노경준", currentUsername);
    }

    @Test
    void signin(){
        // given
        MemberLoginDto memberLoginDto = MemberLoginDto.builder()
                .email("s20018@gsm.hs.kr")
                .password("1234")
                .build();

        // when
        Map<String,String> result = memberService.signin(memberLoginDto);
        System.out.println("================================ " + memberRepository.findByEmail(memberLoginDto.getEmail()).getUsername() +" ====================================");

        // then
        assertNotNull(result);
        assertThat(result.get("username")).isEqualTo(memberRepository.findByEmail(memberLoginDto.getEmail()).getUsername());
    }

    @Test
    void passwordChange(){
        // given
        MemberPasswordDto memberPasswordDto = new MemberPasswordDto();
        memberPasswordDto.setOldPassword("1234");
        memberPasswordDto.setNewPassword("12345");

        // when
        Map<String, String> result = memberService.passwordChange(memberPasswordDto);

        // then
        assertEquals(true,passwordEncoder.matches(memberPasswordDto.getNewPassword(),result.get("노경준")));
    }

}
