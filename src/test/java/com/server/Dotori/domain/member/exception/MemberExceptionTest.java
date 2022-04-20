package com.server.Dotori.domain.member.exception;

import com.server.Dotori.domain.member.EmailCertificate;
import com.server.Dotori.domain.member.dto.MemberDto;
import com.server.Dotori.domain.member.enumType.Gender;
import com.server.Dotori.domain.member.repository.email.EmailCertificateRepository;
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
}
