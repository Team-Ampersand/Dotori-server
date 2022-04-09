package com.server.Dotori.global.util;

import com.server.Dotori.domain.member.Member;
import com.server.Dotori.domain.member.dto.MemberDto;
import com.server.Dotori.domain.member.enumType.Gender;
import com.server.Dotori.domain.member.enumType.Role;
import com.server.Dotori.domain.member.repository.member.MemberRepository;
import com.server.Dotori.domain.member.service.MemberService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class CurrentMemberUtilTest {
    @Autowired
    private CurrentMemberUtil currentMemberUtil;
    @Autowired
    private MemberService memberService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void currentMemberEmail() {
        //given
        MemberDto memberDto = MemberDto.builder()
                .memberName("노경준")
                .stuNum("2206")
                .password("1234")
                .email("s20018@gmail.com")
                .gender(Gender.MAN)
                .build();
        memberRepository.save(
                memberDto.toEntity(
                        passwordEncoder.encode(memberDto.getPassword())
                )
        );

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
        assertEquals("s20018@gmail.com", currentMemberEmail);
    }

    @Test
    public void currentMember() {
        //given
        MemberDto memberDto = MemberDto.builder()
                .memberName("노경준")
                .stuNum("2206")
                .password("1234")
                .email("s20018@gmail.com")
                .build();
        memberService.signup(memberDto);
        System.out.println("======== saved =========");

        // when login session 발급
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                memberDto.getEmail(),
                memberDto.getPassword(),
                List.of(new SimpleGrantedAuthority(Role.ROLE_MEMBER.name())));
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(token);
        System.out.println("=================================");
        System.out.println(context);

        //then
        Member currentMember = currentMemberUtil.getCurrentMember();
        assertEquals(memberDto.getMemberName(), currentMember.getMemberName());
        assertEquals(memberDto.getEmail(), currentMember.getEmail());
    }

}
