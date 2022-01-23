package com.server.Dotori.model.member.service.massage;

import com.server.Dotori.model.massage.repository.MassageRepository;
import com.server.Dotori.model.massage.service.MassageService;
import com.server.Dotori.model.member.dto.MemberDto;
import com.server.Dotori.model.member.enumType.Massage;
import com.server.Dotori.model.member.enumType.Role;
import com.server.Dotori.model.member.repository.member.MemberRepository;
import com.server.Dotori.util.CurrentMemberUtil;
import lombok.RequiredArgsConstructor;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class MassageServiceTest {

    @Autowired MemberRepository memberRepository;
    @Autowired PasswordEncoder passwordEncoder;
    @Autowired CurrentMemberUtil currentMemberUtil;
    @Autowired MassageService massageService;
    @Autowired MassageRepository massageRepository;

    @BeforeEach
    void signupMember() {
        MemberDto memberDto = MemberDto.builder()
                .memberName("김태민")
                .stuNum("2406")
                .password("1234")
                .email("s20014@gsm.hs.kr")
                .build();
        memberRepository.save(
                memberDto.toEntity(
                        passwordEncoder.encode(memberDto.getPassword())
                )
        );
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
        String currentMemberEmail = CurrentMemberUtil.getCurrentEmail();
        assertEquals("s20014@gsm.hs.kr", currentMemberEmail);
    }

    @Test
    @DisplayName("안마의자 신청이 잘 되나요?")
    public void requestMassageTest() {
        massageService.requestMassage(DayOfWeek.WEDNESDAY, 20, 40);

        assertThat(Massage.APPLIED).isEqualTo(currentMemberUtil.getCurrentMember().getMassage());
        assertThat(1).isEqualTo(massageRepository.count());
    }
}
