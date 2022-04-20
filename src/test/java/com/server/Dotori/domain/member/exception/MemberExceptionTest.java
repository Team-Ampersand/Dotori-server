package com.server.Dotori.domain.member.exception;

import com.server.Dotori.domain.member.EmailCertificate;
import com.server.Dotori.domain.member.Member;
import com.server.Dotori.domain.member.dto.EmailDto;
import com.server.Dotori.domain.member.dto.MemberDto;
import com.server.Dotori.domain.member.dto.SignInDto;
import com.server.Dotori.domain.member.dto.SignUpEmailCheckDto;
import com.server.Dotori.domain.member.enumType.Gender;
import com.server.Dotori.domain.member.enumType.Massage;
import com.server.Dotori.domain.member.enumType.Music;
import com.server.Dotori.domain.member.enumType.SelfStudy;
import com.server.Dotori.domain.member.repository.email.EmailCertificateRepository;
import com.server.Dotori.domain.member.repository.member.MemberRepository;
import com.server.Dotori.domain.member.service.MemberService;
import com.server.Dotori.global.exception.DotoriException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Transactional
@SpringBootTest
public class MemberExceptionTest {

    @Autowired private MemberService memberService;
    @Autowired private EmailCertificateRepository emailCertificateRepository;
    @Autowired private MemberRepository memberRepository;

    @Test
    @DisplayName("이미 가입된 이메일 Exception")
    void signup_DataIntegrityViolationException(){
        // given
        MemberDto memberDto1 = MemberDto.builder()
                .memberName("노경준")
                .stuNum("3203")
                .password("1234")
                .email("s20018@gsm.hs.kr")
                .gender(Gender.MAN)
                .build();

        MemberDto memberDto2 = MemberDto.builder()
                .memberName("노경준")
                .stuNum("3204")
                .password("1234")
                .email("s20018@gsm.hs.kr")
                .gender(Gender.MAN)
                .build();

        // when
        memberService.signup(memberDto1);

        // then
        Assertions.assertThrows(DotoriException.class, () -> memberService.signup(memberDto2));
    }

    @Test
    @DisplayName("인증이 되지 않은 유저 Exception")
    void signup_MEMBER_EMAIL_HAS_NOT_BEEN_CERTIFICATE_Exception(){
        // given
        emailCertificateRepository.save(EmailCertificate.builder()
                .member(null)
                .email("s20018@gsm.hs.kr")
                .key("123456")
                .expiredTime(LocalDateTime.now())
                .build());

        MemberDto memberDto = MemberDto.builder()
                .memberName("노경준")
                .stuNum("3203")
                .password("1234")
                .email("s20018@gsm.hs.kr")
                .gender(Gender.MAN)
                .build();

        // when, then
        Assertions.assertThrows(DotoriException.class,() -> memberService.signup(memberDto));
    }

    @Test
    @DisplayName("이미 가입된 Email 과 stuNum Exception")
    void signup_existsByEmailAndStuNumException(){
        // given
        MemberDto memberDto = MemberDto.builder()
                .memberName("노경준")
                .stuNum("3203")
                .password("1234")
                .email("s20018@gsm.hs.kr")
                .gender(Gender.MAN)
                .build();

        // when
        memberService.signup(memberDto);

        // then
        Assertions.assertThrows(DotoriException.class, () -> memberService.signup(memberDto));
    }

    @Test
    void signup_email_exception(){
        memberRepository.save(
                Member.builder()
                        .memberName("노경준")
                        .stuNum("3203")
                        .email("s20018@gsm.hs.kr")
                        .password("1234")
                        .point(1L)
                        .refreshToken(null)
                        .gender(Gender.MAN)
                        .selfStudy(SelfStudy.CAN)
                        .music(Music.CAN)
                        .massage(Massage.CAN)
                        .build()
        );

        EmailDto emailDto = new EmailDto("s20018@gsm.hs.kr");

        Assertions.assertThrows(DotoriException.class,() -> memberService.sendEmailSignup(emailDto));
    }

    @Test
    void signup_email_check_exception(){
        emailCertificateRepository.save(
                EmailCertificate.builder()
                        .email("test@test.com")
                        .key("123456")
                        .expiredTime(LocalDateTime.now().plusMinutes(5))
                        .build()
        );

        SignUpEmailCheckDto signUpEmailCheckDto = new SignUpEmailCheckDto("000000");
        Assertions.assertThrows(DotoriException.class,() -> memberService.checkEmailSignup(signUpEmailCheckDto));
    }

    @Test
    void signin_member_not_found(){
        SignInDto signInDto = SignInDto.builder()
                .email("s20018@gsm.hs.kr")
                .password("1234")
                .build();

        Assertions.assertThrows(DotoriException.class,() -> memberService.signIn(signInDto));
    }

    @Test
    void signin_password_not_matching(){
        memberRepository.save(
                Member.builder()
                        .memberName("노경준")
                        .stuNum("3203")
                        .email("s20018@gsm.hs.kr")
                        .password("1234")
                        .point(1L)
                        .refreshToken(null)
                        .gender(Gender.MAN)
                        .selfStudy(SelfStudy.CAN)
                        .music(Music.CAN)
                        .massage(Massage.CAN)
                        .build()
        );

        SignInDto signInDto = SignInDto.builder()
                .email("s20018@gsm.hs.kr")
                .password("0000")
                .build();

        Assertions.assertThrows(DotoriException.class,() -> memberService.signIn(signInDto));
    }
}
