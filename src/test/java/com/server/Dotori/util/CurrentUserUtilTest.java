package com.server.Dotori.util;

import com.server.Dotori.model.member.Member;
import com.server.Dotori.model.member.dto.MemberDto;
import com.server.Dotori.model.member.enumType.Role;
import com.server.Dotori.model.member.repository.MemberRepository;
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
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class CurrentUserUtilTest {
    @Autowired
    private CurrentUserUtil currentUserUtil;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void currentUserNickname() {
        //given
        MemberDto userDto = MemberDto.builder()
                .username("노경준")
                .stdNum("1")
                .password("1234")
                .email("123@naver.com")
                .build();

        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        memberRepository.save(userDto.toEntity(Role.ROLE_ADMIN));
        System.out.println("======== saved =========");

        // when login session 발급
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                userDto.getUsername(),
                userDto.getPassword(),
                List.of(new SimpleGrantedAuthority(Role.ROLE_MEMBER.name())));

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(token);
        System.out.println("=================================");
        System.out.println(context);

        //then
        String currentUserNickname = CurrentUserUtil.getCurrentUserNickname();
        assertEquals("노경준", currentUserNickname);
    }

    @Test
    public void currentUser() {
        //given
        MemberDto memberDto = MemberDto.builder()
                .username("노경준")
                .stdNum("1")
                .password("1234")
                .email("123@naver.com")
                .build();
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));
        memberRepository.save(memberDto.toEntity(Role.ROLE_ADMIN));
        System.out.println("======== saved =========");

        // when login session 발급
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                memberDto.getUsername(),
                memberDto.getPassword(),
                List.of(new SimpleGrantedAuthority(Role.ROLE_MEMBER.name())));
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(token);
        System.out.println("=================================");
        System.out.println(context);

        //then
        Member currentUser = currentUserUtil.getCurrentUser();
        assertTrue(currentUser != null, "true");
        assertEquals(memberDto.toEntity(Role.ROLE_ADMIN).getUsername(), currentUser.getUsername());
        assertEquals(memberDto.toEntity(Role.ROLE_ADMIN).getEmail(), currentUser.getEmail());
    }


}


