package com.server.Dotori.model.member.service;

import com.server.Dotori.model.member.dto.MemberDeleteDto;
import com.server.Dotori.model.member.dto.MemberDto;
import com.server.Dotori.model.member.dto.SignInDto;
import com.server.Dotori.model.member.dto.ChangePasswordDto;
import com.server.Dotori.model.member.enumType.Role;
import com.server.Dotori.model.member.repository.member.MemberRepository;
import com.server.Dotori.util.CurrentMemberUtil;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


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
                .memberName("관리자")
                .stuNum("1111")
                .password("1234")
                .email("s20000@gsm.hs.kr")
                .build();
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));

        // when
        Long result = memberService.signup(memberDto);

        // then
        assertThat(result).isEqualTo(memberRepository.findByEmail(memberDto.getEmail()).orElseThrow().getId());
    }

    @BeforeEach
    @DisplayName("로그인 되어있는 유저를 확인하는 테스트")
    void currentMember() {
        //given
        MemberDto memberDto = MemberDto.builder()
                .memberName("노경준")
                .stuNum("2206")
                .password("1234")
                .email("s20018@gsm.hs.kr")
                .build();
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));
        memberRepository.save(memberDto.toEntity());
        System.out.println("======== saved =========");

        // when login session 발급
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                memberDto.getEmail(),
                memberDto.getPassword(),
                List.of(new SimpleGrantedAuthority(Role.ROLE_ADMIN.name())));
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(token);
        System.out.println("=================================");
        System.out.println(context);

        //then
        String currentEmail = CurrentMemberUtil.getCurrentEmail();
        assertEquals("s20018@gsm.hs.kr", currentEmail);
    }

    @Test
    void signin(){
        // given
        SignInDto memberLoginDto = SignInDto.builder()
                .email("s20018@gsm.hs.kr")
                .password("1234")
                .build();

        // when
        Map<String,String> result = memberService.signIn(memberLoginDto);
        System.out.println("================================ " + memberRepository.findByEmail(memberLoginDto.getEmail()).orElseThrow().getUsername() +" ====================================");

        // then
        assertNotNull(result);
        assertThat(result.get("email")).isEqualTo(memberRepository.findByEmail(memberLoginDto.getEmail()).orElseThrow().getEmail());
    }

    @Test
    void passwordChange(){
        // given
        ChangePasswordDto memberPasswordDto = new ChangePasswordDto();
        memberPasswordDto.setCurrentPassword("1234");
        memberPasswordDto.setNewPassword("12345");

        // when
        String result = memberService.changePassword(memberPasswordDto);

        // then
        assertEquals(true,passwordEncoder.matches(memberPasswordDto.getNewPassword(),result));
    }

    @Test
    @DisplayName("로그아웃")
    void logout(){
        // when
        SignInDto memberLoginDto = SignInDto.builder()
                .email("s20018@gsm.hs.kr")
                .password("1234")
                .build();

        // given
        memberService.signIn(memberLoginDto);
        memberService.logout();

        // then
        assertNull(memberRepository.findByEmail(memberLoginDto.getEmail()).orElseThrow().getRefreshToken());
    }

    @Test
    @DisplayName("회원탈퇴")
    void delete(){
        // when
        SignInDto memberLoginDto = SignInDto.builder()
                .email("s20018@gsm.hs.kr")
                .password("1234")
                .build();

        MemberDeleteDto memberDeleteDto = new MemberDeleteDto();
        memberDeleteDto.setEmail("s20018@gsm.hs.kr");
        memberDeleteDto.setPassword("1234");

        // given
        memberService.signIn(memberLoginDto);
        memberService.delete(memberDeleteDto);

        // then
        assertThat(memberRepository.findByEmail(memberDeleteDto.getEmail())).isEqualTo(Optional.empty());
    }
}
