package com.server.Dotori.model.member;

import com.server.Dotori.config.querydsl.QuerydslConfiguration;
import com.server.Dotori.model.member.dto.MemberDto;
import com.server.Dotori.model.member.enumType.Music;
import com.server.Dotori.model.member.enumType.Role;
import com.server.Dotori.model.member.enumType.SelfStudy;
import com.server.Dotori.model.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class MemberTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("Member가 Save가 되는지 검증")
    void Member가_save된다(){

        // Given
        Member member = Member.builder()
                .username("taemin")
                .stdNum("2406")
                .password("1234")
                .email("s20014@gsm.hs.kr")
                .selfStudy(SelfStudy.CAN)
                .music(Music.CAN)
                .roles(Collections.singletonList(Role.ROLE_DEVELOPER))
                .point(0L)
                .build();

        // When

        Member saveMember = memberRepository.save(member);
        System.out.println("======== save =========");


        // Then
        assertEquals(member, saveMember);

    }



}
