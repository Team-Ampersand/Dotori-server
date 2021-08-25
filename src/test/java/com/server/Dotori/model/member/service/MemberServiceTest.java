package com.server.Dotori.model.member.service;

import com.server.Dotori.model.member.dto.MemberDto;
import com.server.Dotori.model.member.enumType.Role;
import com.server.Dotori.model.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class MemberServiceTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void singup(){
        //given
        MemberDto memberDto = MemberDto.builder()
                .username("노경준")
                .stdNum("2206")
                .password("1234")
                .email("s20018@gsm.hs.kr")
                .build();
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));

        //when
        memberRepository.save(memberDto.toEntity(Role.ROLE_ADMIN));

        //then
        assertThat(memberDto.toEntity(Role.ROLE_ADMIN).getUsername()).isEqualTo(memberRepository.findByUsername("노경준").getUsername());
    }
}
