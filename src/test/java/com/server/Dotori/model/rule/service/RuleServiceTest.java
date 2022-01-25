package com.server.Dotori.model.rule.service;

import com.server.Dotori.exception.member.exception.MemberNotFoundException;
import com.server.Dotori.model.member.dto.MemberDto;
import com.server.Dotori.model.member.repository.member.MemberRepository;
import com.server.Dotori.model.rule.dto.RuleGrantDto;
import com.server.Dotori.model.rule.dto.RulesCntAndDatesDto;
import com.server.Dotori.model.rule.enumType.Rule;
import com.server.Dotori.model.rule.repository.RuleRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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
        memberRepository.save(
                memberDto1.toEntity(
                        passwordEncoder.encode(memberDto1.getPassword())
                )
        );

        MemberDto memberDto2 = MemberDto.builder()
                .memberName("송시현")
                .stuNum("2212")
                .password("1234")
                .email("s20040@gsm.hs.kr")
                .build();
        memberRepository.save(
                memberDto2.toEntity(
                        passwordEncoder.encode(memberDto2.getPassword())
                )
        );

        assertNotNull(memberRepository.findByEmail(memberDto1.getEmail()));
        assertNotNull(memberRepository.findByEmail(memberDto2.getEmail()));
    }
    @Order(value = 2)
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
        assertThat(ruleRepository.findById(2L).get().getRule()).isEqualTo(Rule.FIREARMS1);
    }

    @Order(value = 3)
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

    @Order(value = 4)
    @Test
    @DisplayName("기숙사 규정사항을 어긴 학생의 어떤 규정을 어겼는지 조회하는 테스트")
    void findViolationOfTheRulesTest() {
        // given
        List<String> stuNumList = new ArrayList<>();
        stuNumList.add("2206");
        stuNumList.add("2212");

        ruleService.grant(
            RuleGrantDto.builder()
                    .stuNum(stuNumList)
                    .rule(Rule.FIREARMS1)
                    .build()
        );

        // when
        HashMap<Rule, RulesCntAndDatesDto> violationOfTheRules = ruleService.findAllViolationOfTheRule("2206");

        // then
        assertThat(violationOfTheRules.get(Rule.FIREARMS1).getCnt()).isEqualTo(1);
    }

    @Order(value = 1)
    @Test
    @DisplayName("기숙사 규정을어긴 리스트에서 삭제를 할 수 있는 테스트 코드")
    void deleteViolationOfTheRulesTest(){
        // given
        List<String> stuNumList = new ArrayList<>() {
            {
                add("2206");
            }
        };

        ruleService.grant(RuleGrantDto.builder()
                .stuNum(stuNumList)
                .rule(Rule.FIREARMS1)
                .build());

        // when
        ruleService.deleteViolationOfTheRules(1L);

        // then
        assertThat(ruleRepository.findById(1L).isEmpty()).isTrue();
    }
}