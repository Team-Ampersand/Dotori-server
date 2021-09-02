package com.server.Dotori.model.member.service.selfstudy;

import com.server.Dotori.model.member.Member;
import com.server.Dotori.model.member.dto.MemberDto;
import com.server.Dotori.model.member.enumType.Role;
import com.server.Dotori.model.member.enumType.SelfStudy;
import com.server.Dotori.model.member.repository.MemberRepository;
import com.server.Dotori.model.member.dto.selfstudy.SelfStudyStudentsDto;
import com.server.Dotori.model.member.service.selfstudy.SelfStudyService;
import com.server.Dotori.util.CurrentUserUtil;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static com.server.Dotori.model.member.enumType.Music.CAN;
import static com.server.Dotori.model.member.enumType.SelfStudy.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.MethodOrderer.*;

@SpringBootTest
@Transactional
@TestMethodOrder(OrderAnnotation.class)
class SelfStudyServiceTest {

    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private MemberRepository memberRepository;
    @Autowired private CurrentUserUtil currentUserUtil;
    @Autowired private SelfStudyService selfStudyService;
    @Autowired
    EntityManager em;

    @BeforeEach
    @DisplayName("로그인 되어있는 유저를 확인하는 테스트")
    void currentUser() {
        //given
        MemberDto memberDto = MemberDto.builder()
                .username("배태현")
                .stdNum("2409")
                .password("0809")
                .email("s20032@gsm.hs.kr")
                .answer("배털")
                .build();
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));
        memberRepository.save(memberDto.toEntity(Role.ROLE_ADMIN));
        System.out.println("======== saved =========");

        // when login session 발급
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                memberDto.getUsername(),
                memberDto.getPassword(),
                List.of(new SimpleGrantedAuthority(Role.ROLE_ADMIN.name())));
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(token);
        System.out.println("=================================");
        System.out.println(context);

        //then
        String currentUsername = CurrentUserUtil.getCurrentUserNickname();
        assertEquals("배태현", currentUsername);
    }

    @Test
    @DisplayName("자습신청이 제대로 되나요?")
    public void requestSelfStudyTest() {
        selfStudyService.requestSelfStudy();

        assertEquals(SelfStudy.APPLIED, currentUserUtil.getCurrentUser().getSelfStudy());
    }

    @Test
    @DisplayName("자습신청 취소가 제대로 되나요?")
    public void cancelSelfStudy() {
        selfStudyService.requestSelfStudy();
        selfStudyService.cancelSelfStudy();

        assertEquals(CANT, currentUserUtil.getCurrentUser().getSelfStudy());
    }

    @Test
    @DisplayName("자습 신청한 학생들 목록이 잘 조회 되나요?")
    public void getSelfStudyStudents() {
        //given //when
        selfStudyService.requestSelfStudy();
        List<SelfStudyStudentsDto> selfStudyStudents = selfStudyService.getSelfStudyStudents();

        //then
        assertEquals(1, selfStudyStudents.size());
    }

    @Test
    @DisplayName("학생들의 자습신청 상태가 잘 변경되나요?")
    public void updateSelfStudyStatus() {
        //given
        memberRepository.save(
                Member.builder()
                        .username("qoxogus1")
                        .stdNum("2410")
                        .password("1234")
                        .email("s20033@gsm.hs.kr")
                        .roles(Collections.singletonList(Role.ROLE_ADMIN))
                        .music(CAN)
                        .selfStudy(SelfStudy.APPLIED)
                        .point(0L)
                        .answer("배털")
                        .build()
        );

        memberRepository.save(
                Member.builder()
                        .username("qwer")
                        .stdNum("2408")
                        .password("1234")
                        .email("s20031@gsm.hs.kr")
                        .roles(Collections.singletonList(Role.ROLE_ADMIN))
                        .music(CAN)
                        .selfStudy(CANT)
                        .point(0L)
                        .answer("배털")
                        .build()
        );

        memberRepository.save(
                Member.builder()
                        .username("rewq")
                        .stdNum("2407")
                        .password("1234")
                        .email("s20030@gsm.hs.kr")
                        .roles(Collections.singletonList(Role.ROLE_ADMIN))
                        .music(CAN)
                        .selfStudy(SelfStudy.CAN)
                        .point(0L)
                        .answer("배털")
                        .build()
        );

        //when
        selfStudyService.updateSelfStudyStatus();

        em.flush();
        em.clear();

        //then
        assertEquals(SelfStudy.CAN, memberRepository.findByUsername("qoxogus1").getSelfStudy());
        assertEquals(SelfStudy.CAN, memberRepository.findByUsername("qwer").getSelfStudy());
        assertEquals(SelfStudy.CAN, memberRepository.findByUsername("rewq").getSelfStudy()); //이 회원은 그대로 CAN
    }

    @Test
    @Order(1)
    @DisplayName("자습신청한 학생 수 카운트가 잘 세지나요?")
    public void selfStudyCountTest() {
        //given //when
        selfStudyService.requestSelfStudy();

        Integer selfStudyCount = selfStudyService.selfStudyCount();

        //then
        assertEquals(1, selfStudyCount);
    }
}