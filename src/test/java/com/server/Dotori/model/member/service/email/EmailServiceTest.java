package com.server.Dotori.model.member.service.email;

import com.server.Dotori.exception.member.exception.MemberAuthenticationAnswerNotMatchingException;
import com.server.Dotori.model.member.dto.EmailDto;
import com.server.Dotori.model.member.dto.MemberDto;
import com.server.Dotori.model.member.repository.email.EmailCertificateRepository;

import org.junit.jupiter.api.Disabled;
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
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailCertificateRepository emailCertificateRepository;

    @Disabled
    @Test
    void authKey(){
        //given
        MemberDto memberDto = MemberDto.builder()
                .memberName("노경준")
                .stuNum("2206")
                .password("1234")
                .email("s20018@gsm.hs.kr")
                .build();
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));

        EmailDto emailDto = new EmailDto();
        emailDto.setEmail(memberDto.getEmail());

        //when
        String key = emailService.authKey(emailDto);

        //then
        assertThat(key).isEqualTo(emailCertificateRepository.findByKey(key).orElseThrow(MemberAuthenticationAnswerNotMatchingException::new).getKey());
    }


}
