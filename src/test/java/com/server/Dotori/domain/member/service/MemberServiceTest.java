package com.server.Dotori.domain.member.service;

import com.server.Dotori.domain.member.EmailCertificate;
import com.server.Dotori.domain.member.Member;
import com.server.Dotori.domain.member.dto.*;
import com.server.Dotori.domain.member.enumType.*;
import com.server.Dotori.domain.member.repository.email.EmailCertificateRepository;
import com.server.Dotori.domain.member.repository.member.MemberRepository;
import com.server.Dotori.global.util.CurrentMemberUtil;
import org.junit.jupiter.api.Assertions;
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
import java.time.LocalDateTime;
import java.util.List;
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

    @Autowired
    private EmailCertificateRepository emailCertificateRepository;

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
                memberDto.toEntity(passwordEncoder.encode(memberDto.getPassword()))
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
    @DisplayName("회원가입 테스트")
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
    @DisplayName("회원가입 시 이메일 인증 보내기 테스트")
    void sendEmailSignup(){
        EmailDto emailDto = new EmailDto("test@test.com");

        Assertions.assertDoesNotThrow(() -> memberService.sendEmailSignup(emailDto));
    }

    @Test
    @DisplayName("회원가입 시 이메일 인증 확인 테스트")
    void checkEmailSignup(){
        emailCertificateRepository.save(
                EmailCertificate.builder()
                        .email("test@test.com")
                        .key("123456")
                        .expiredTime(LocalDateTime.now().plusMinutes(5))
                        .build()
        );

        SignUpEmailCheckDto signUpEmailCheckDto = new SignUpEmailCheckDto("123456");
        Assertions.assertDoesNotThrow(() -> memberService.checkEmailSignup(signUpEmailCheckDto));
    }

    @Test
    @DisplayName("로그인 테스트")
    void signin(){
        // given
        SignInDto memberLoginDto = SignInDto.builder()
                .email("s20018@gsm.hs.kr")
                .password("1234")
                .build();

        // when
        SignInResponseDto result = memberService.signIn(memberLoginDto);

        // then
        assertNotNull(result);
        assertThat(result.getGender()).isEqualTo(Gender.MAN);
    }

    @Test
    @DisplayName("비밀번호 변경 테스트")
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
    @DisplayName("비밀번호 변경 시 이메일 인증 보내기 테스트")
    void sendEmailChangePasswordTest(){
        // given
        memberRepository.save(
                Member.builder()
                        .memberName("노경준")
                        .stuNum("1000")
                        .email("test@test.com")
                        .password("1234")
                        .point(1L)
                        .refreshToken(null)
                        .gender(Gender.MAN)
                        .selfStudy(SelfStudy.CAN)
                        .music(Music.CAN)
                        .massage(Massage.CAN)
                        .build()
        );

        EmailDto emailDto = new EmailDto("test@test.com");

        // when, then
        assertDoesNotThrow(() -> memberService.sendEmailChangePassword(emailDto));
    }

    @Test
    @DisplayName("비밀번호 변경 시 이메일 인증 확인 테스트")
    void checkEmailChangePassword(){
        // given
        memberRepository.save(
                Member.builder()
                        .memberName("노경준")
                        .stuNum("1000")
                        .email("test@test.com")
                        .password("1234")
                        .point(1L)
                        .refreshToken(null)
                        .gender(Gender.MAN)
                        .selfStudy(SelfStudy.CAN)
                        .music(Music.CAN)
                        .massage(Massage.CAN)
                        .build()
        );

        emailCertificateRepository.save(
                EmailCertificate.builder()
                        .member(null)
                        .email("test@test.com")
                        .key("123456")
                        .expiredTime(LocalDateTime.now().plusMinutes(5))
                        .build()
        );

        ChangePasswordEmailCheckDto changePasswordEmailCheckDto = new ChangePasswordEmailCheckDto("123456","12345");

        assertDoesNotThrow(() -> memberService.checkEmailChangePassword(changePasswordEmailCheckDto));
    }

    @Test
    @DisplayName("성별 변경 테스트")
    void setGender(){
        memberRepository.save(
                Member.builder()
                        .memberName("노경준")
                        .stuNum("1000")
                        .email("test@test.com")
                        .password("1234")
                        .point(1L)
                        .refreshToken(null)
                        .gender(Gender.PENDING)
                        .selfStudy(SelfStudy.CAN)
                        .music(Music.CAN)
                        .massage(Massage.CAN)
                        .build()
        );

        SetGenderDto setGenderDto = new SetGenderDto("test@test.com",Gender.MAN);

        assertDoesNotThrow(() -> memberService.setGender(setGenderDto));
    }

    @Test
    @DisplayName("로그아웃 테스트")
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
    @DisplayName("회원탈퇴 테스트")
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
