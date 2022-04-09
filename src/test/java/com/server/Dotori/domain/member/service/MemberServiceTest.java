package com.server.Dotori.domain.member.service;

import com.server.Dotori.domain.member.dto.ChangePasswordDto;
import com.server.Dotori.domain.member.dto.MemberDto;
import com.server.Dotori.domain.member.dto.SignInDto;
import com.server.Dotori.domain.member.dto.WithdrawlDto;
import com.server.Dotori.domain.member.enumType.Gender;
import com.server.Dotori.domain.member.enumType.Role;
import com.server.Dotori.domain.member.repository.member.MemberRepository;
import com.server.Dotori.global.util.CurrentMemberUtil;
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

import static org.assertj.core.api.Assertions.assertThat;
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

    @BeforeEach
    @DisplayName("로그인 되어있는 유저를 확인하는 테스트")
    void currentMember() {
        //given
        MemberDto memberDto = MemberDto.builder()
                .memberName("노경준")
                .stuNum("2206")
                .password("1234")
                .email("s20018@gsm.hs.kr")
                .gender(Gender.MAN)
                .build();

        memberRepository.save(
                memberDto.toEntity(passwordEncoder.encode(memberDto.getPassword()),memberDto.getGender())
        );

        // when login session 발급
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                memberDto.getEmail(),
                memberDto.getPassword(),
                List.of(new SimpleGrantedAuthority(Role.ROLE_ADMIN.name())));
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(token);
        System.out.println(context);

        //then
        String currentEmail = CurrentMemberUtil.getCurrentEmail();
        assertEquals("s20018@gsm.hs.kr", currentEmail);
    }

    @Test
    void signup(){
        // given
        MemberDto memberDto = MemberDto.builder()
                .memberName("관리자")
                .stuNum("1111")
                .password("1234")
                .email("s20000@gsm.hs.kr")
                .gender(Gender.MAN)
                .build();

        // when
        Long result = memberService.signup(memberDto);

        // then
        assertThat(result).isEqualTo(memberRepository.findByEmail(memberDto.getEmail()).orElseThrow().getId());
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

        // then
        assertNotNull(result);
        assertThat(result.get("email")).isEqualTo(memberRepository.findByEmail(memberLoginDto.getEmail()).orElseThrow().getEmail());
    }

    @Test
    void passwordChange(){
        // given
        ChangePasswordDto changePasswordDto = ChangePasswordDto.builder()
                .currentPassword("1234")
                .newPassword("12345")
                .build();

        // when
        String result = memberService.changePassword(changePasswordDto);

        // then
        assertEquals(true,passwordEncoder.matches(changePasswordDto.getNewPassword(),result));
    }

    @Test
    @DisplayName("로그아웃")
    void logout(){
        // when
        SignInDto signInDto = SignInDto.builder()
                .email("s20018@gsm.hs.kr")
                .password("1234")
                .build();

        // given
        memberService.signIn(signInDto);
        memberService.logout();

        // then
        assertNull(memberRepository.findByEmail(signInDto.getEmail()).orElseThrow().getRefreshToken());
    }

    @Test
    @DisplayName("회원탈퇴")
    void delete(){
        // when
        SignInDto memberLoginDto = SignInDto.builder()
                .email("s20018@gsm.hs.kr")
                .password("1234")
                .build();

        WithdrawlDto withdrawlDto = new WithdrawlDto("s20018@gsm.hs.kr","1234");

        // given
        memberService.signIn(memberLoginDto);
        memberService.withdrawal(withdrawlDto);

        // then
        assertThat(memberRepository.findByEmail(withdrawlDto.getEmail())).isEqualTo(Optional.empty());
    }
}
