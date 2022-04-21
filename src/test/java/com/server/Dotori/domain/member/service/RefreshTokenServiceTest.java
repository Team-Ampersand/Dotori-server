package com.server.Dotori.domain.member.service;

import com.server.Dotori.domain.member.dto.MemberDto;
import com.server.Dotori.domain.member.dto.RefreshTokenDto;
import com.server.Dotori.domain.member.dto.SignInDto;
import com.server.Dotori.domain.member.enumType.Gender;
import com.server.Dotori.domain.member.enumType.Role;
import com.server.Dotori.domain.member.repository.member.MemberRepository;
import com.server.Dotori.global.security.jwt.JwtTokenProvider;
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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest
public class RefreshTokenServiceTest {

    @Autowired private MemberRepository memberRepository;
    @Autowired private RefreshTokenService refreshTokenService;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtTokenProvider jwtTokenProvider;
    @Autowired private MemberService memberService;
    @Autowired private CurrentMemberUtil currentMemberUtil;

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
    void refreshToken(){
        SignInDto signInDto = SignInDto.builder()
                .email("s20018@gsm.hs.kr")
                .password("1234")
                .build();
        memberService.signIn(signInDto);

        String refreshToken = currentMemberUtil.getCurrentMember().getRefreshToken();

        RefreshTokenDto refreshTokenDto = new RefreshTokenDto("s20018@gsm.hs.kr");
        assertDoesNotThrow(() -> refreshTokenService.refreshToken(refreshToken, refreshTokenDto));
    }
}
