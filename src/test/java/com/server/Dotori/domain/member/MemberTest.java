package com.server.Dotori.domain.member;

import com.server.Dotori.domain.member.enumType.Music;
import com.server.Dotori.domain.member.enumType.Role;
import com.server.Dotori.domain.member.enumType.SelfStudy;
import com.server.Dotori.domain.member.repository.member.MemberRepository;
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
                        .memberName("taemin")
                        .stuNum("2406")
                        .password("1234")
                        .email("s20014@gsm.hs.kr")
                        .roles(Collections.singletonList(Role.ROLE_ADMIN))
                        .music(Music.CAN)
                        .selfStudy(SelfStudy.CAN)
                        .point(0L)
                        .build()
        );


        // Then
        Assertions.assertThat(saveMember.getMemberName()).isEqualTo("taemin");

    }

}
