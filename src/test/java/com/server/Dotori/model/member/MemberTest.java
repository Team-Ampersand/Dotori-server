package com.server.Dotori.model.member;

import com.server.Dotori.model.member.enumType.Music;
import com.server.Dotori.model.member.enumType.Role;
import com.server.Dotori.model.member.enumType.SelfStudy;
import com.server.Dotori.model.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;


@SpringBootTest
@Transactional
public class MemberTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("Member가 Save가 되는지 검증")
    void Member가_save된다(){

        // Given // When
        Member saveMember = memberRepository.save(
                Member.builder()
                        .username("taemin")
                        .stdNum("2406")
                        .password("1234")
                        .email("s20014@gsm.hs.kr")
                        .roles(Collections.singletonList(Role.ROLE_ADMIN))
                        .music(Music.CAN)
                        .selfStudy(SelfStudy.CAN)
                        .point(0L)
                        .answer("때밀이")
                        .build()
        );


        // Then
        Assertions.assertThat(saveMember.getUsername()).isEqualTo("taemin");

    }



}
