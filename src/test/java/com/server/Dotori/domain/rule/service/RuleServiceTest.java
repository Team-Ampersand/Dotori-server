package com.server.Dotori.domain.rule.service;

import com.server.Dotori.domain.member.dto.MemberDto;
import com.server.Dotori.domain.member.enumType.Gender;
import com.server.Dotori.domain.member.enumType.Role;
import com.server.Dotori.domain.member.repository.member.MemberRepository;
import com.server.Dotori.domain.rule.dto.RuleGrantDto;
import com.server.Dotori.domain.rule.dto.RulesCntAndDatesDto;
import com.server.Dotori.domain.rule.enumType.Rule;
import com.server.Dotori.domain.rule.repository.RuleRepository;
import com.server.Dotori.global.exception.DotoriException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
                .gender(Gender.MAN)
                .build();

        memberRepository.save(
                memberDto1.toEntity(
                        passwordEncoder.encode(memberDto1.getPassword())
                )
        );

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                memberDto1.getEmail(),
                memberDto1.getPassword(),
                List.of(new SimpleGrantedAuthority(Role.ROLE_ADMIN.name())));
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(token);
        System.out.println(context);

        MemberDto memberDto2 = MemberDto.builder()
                .memberName("송시현")
                .stuNum("2212")
                .password("1234")
                .email("s20040@gsm.hs.kr")
                .gender(Gender.MAN)
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
                .rule(List.of(Rule.FIREARMS))
                .date(LocalDate.now())
                .build());

        // then
        assertThat(ruleRepository.findById(2L).get().getRule()).isEqualTo(Rule.FIREARMS);
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
                DotoriException.class,
                () -> ruleService.grant(RuleGrantDto.builder()
                        .stuNum(stuNumList)
                        .rule(List.of(Rule.FIREARMS))
                        .date(LocalDate.now())
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
                    .rule(List.of(Rule.FIREARMS))
                    .date(LocalDate.now())
                    .build()
        );

        // when
        HashMap<Rule, RulesCntAndDatesDto> violationOfTheRules = ruleService.findAllViolationOfTheRule("2206");

        // then
        assertThat(violationOfTheRules.get(Rule.FIREARMS).getCnt()).isEqualTo(1);
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
                .rule(List.of(Rule.FIREARMS))
                .date(LocalDate.now())
                .build());

        // when
        ruleService.deleteViolationOfTheRules(1L);

        // then
        assertThat(ruleRepository.findById(1L).isEmpty()).isTrue();
    }
}