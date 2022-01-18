package com.server.Dotori.model.rule.service;

import com.server.Dotori.exception.member.exception.MemberNotFoundException;
import com.server.Dotori.model.member.dto.MemberDto;
import com.server.Dotori.model.member.repository.member.MemberRepository;
import com.server.Dotori.model.member.service.MemberService;
import com.server.Dotori.model.rule.dto.RuleGrantDto;
import com.server.Dotori.model.rule.enumType.Rule;
import com.server.Dotori.model.rule.repository.RuleRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@SpringBootTest
public class RuleServiceTest {

    @Autowired private RuleService ruleService;
    @Autowired private RuleRepository ruleRepository;
    @Autowired private MemberRepository memberRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @BeforeEach
    @DisplayName("로직을 수행전 회원가입을 시키는 테스트")
    void signup(){
        MemberDto memberDto1 = MemberDto.builder()
                .memberName("노경준")
                .stuNum("2206")
                .password("1234")
                .email("s20018@gsm.hs.kr")
                .build();
        memberDto1.setPassword(passwordEncoder.encode(memberDto1.getPassword()));
        memberRepository.save(memberDto1.toEntity());

        MemberDto memberDto2 = MemberDto.builder()
                .memberName("송시현")
                .stuNum("2212")
                .password("1234")
                .email("s20040@gsm.hs.kr")
                .build();
        memberDto2.setPassword(passwordEncoder.encode(memberDto2.getPassword()));
        memberRepository.save(memberDto2.toEntity());

        assertNotNull(memberRepository.findByEmail(memberDto1.getEmail()));
        assertNotNull(memberRepository.findByEmail(memberDto2.getEmail()));
    }

    @Test
    @DisplayName("기숙사 규정을 어겨 RuleViolation 테이블에 멤버와 어긴 규정을 저장하는 테스트 코드")
    void grantTest(){
        // given
        List<String> stuNumList = new ArrayList<>() {
            {
                add("2206");
                add("2212");
            }
        };

        // when
        ruleService.grant(RuleGrantDto.builder()
                .stuNum(stuNumList)
                .rule(Rule.FIREARMS1)
                .build());

        // then
        assertThat(ruleRepository.findById(1L).get().getRule()).isEqualTo(Rule.FIREARMS1);
        assertThat(ruleRepository.findById(2L).get().getRule()).isEqualTo(Rule.FIREARMS1);
    }

    @Test
    @DisplayName("grant 서비스 로직의 Exception 이 잘 작동하는지 확인하는 테스트")
    void grantExceptionTest(){
        // given
        List<String> stuNumList = new ArrayList<>() {
            {
                add("2206");
                add("2212");
                add("2413");
            }
        };

        // when // then
        assertThrows(
                MemberNotFoundException.class,
                () -> ruleService.grant(RuleGrantDto.builder()
                        .stuNum(stuNumList)
                        .rule(Rule.FIREARMS1)
                        .build())
        );
    }

}
