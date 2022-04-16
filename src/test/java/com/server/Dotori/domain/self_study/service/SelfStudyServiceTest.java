package com.server.Dotori.domain.self_study.service;

import com.server.Dotori.domain.member.Member;
import com.server.Dotori.domain.member.dto.MemberDto;
import com.server.Dotori.domain.member.enumType.Role;
import com.server.Dotori.domain.member.enumType.SelfStudy;
import com.server.Dotori.domain.member.repository.member.MemberRepository;
import com.server.Dotori.domain.self_study.dto.SelfStudyStudentsDto;
import com.server.Dotori.domain.self_study.repository.SelfStudyRepository;
import com.server.Dotori.global.exception.DotoriException;
import com.server.Dotori.global.util.CurrentMemberUtil;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.server.Dotori.domain.member.enumType.Gender.MAN;
import static com.server.Dotori.domain.member.enumType.Music.CAN;
import static com.server.Dotori.domain.member.enumType.SelfStudy.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

@SpringBootTest
@Transactional
@TestMethodOrder(OrderAnnotation.class)
class SelfStudyServiceTest {

    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private MemberRepository memberRepository;
    @Autowired private CurrentMemberUtil currentMemberUtil;
    @Autowired private SelfStudyService selfStudyService;
    @Autowired private SelfStudyRepository selfStudyRepository;
    @Autowired
    EntityManager em;

    @BeforeEach
    @DisplayName("로그인 되어있는 유저를 확인하는 테스트")
    void currentUser() {
        //given
        MemberDto memberDto = MemberDto.builder()
                .memberName("배태현")
                .stuNum("2409")
                .password("0809")
                .email("s20032@gsm.hs.kr")
                .gender(MAN)
                .build();
        memberRepository.save(
                memberDto.toEntity(
                        passwordEncoder.encode(memberDto.getPassword())
                )
        );
        System.out.println("======== saved =========");

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
        assertEquals("s20032@gsm.hs.kr", currentMemberEmail);
    }

    @Test
    @DisplayName("자습신청이 제대로 되나요?")
    public void requestSelfStudyTest() {
        selfStudyService.requestSelfStudy(DayOfWeek.MONDAY, 20); // 월요일 8시

        assertEquals(APPLIED, currentMemberUtil.getCurrentMember().getSelfStudy());
        assertEquals(1 , selfStudyRepository.findAll().size());
    }

    @Test
    @DisplayName("적절한 날짜 혹은 시간이 아닐 때 자습신청을 하면 예외가 제대로 터지나요?")
    public void requestSelfStudyExceptionTest() {
        assertThrows(
                DotoriException.class,
                () -> selfStudyService.requestSelfStudy(DayOfWeek.FRIDAY, 20)
        );

        assertThrows(
                DotoriException.class,
                () -> selfStudyService.requestSelfStudy(DayOfWeek.MONDAY, 19)
        );
    }

    @Test
    @DisplayName("자습신청 취소가 제대로 되나요?")
    public void cancelSelfStudy() {
        selfStudyService.requestSelfStudy(DayOfWeek.MONDAY, 20);
        selfStudyService.cancelSelfStudy(DayOfWeek.MONDAY, 20);

        assertEquals(CANT, currentMemberUtil.getCurrentMember().getSelfStudy());
        assertEquals(0, selfStudyRepository.count());
    }

    @Test
    @DisplayName("적절한 날짜 혹은 시간이 아닐 때 자습신청 취소를 하면 예외가 제대로 터지나요?")
    public void cancelSelfStudyExceptionTest() {
        assertThrows(
                DotoriException.class,
                () -> selfStudyService.cancelSelfStudy(DayOfWeek.FRIDAY, 20)
        );

        assertThrows(
                DotoriException.class,
                () -> selfStudyService.cancelSelfStudy(DayOfWeek.MONDAY, 19)
        );
    }

    @Test
    @DisplayName("자습 신청한 학생들 목록이 잘 조회 되나요?")
    public void getSelfStudyStudents() {
        //given //when
        selfStudyService.requestSelfStudy(DayOfWeek.MONDAY, 20);
        List<SelfStudyStudentsDto> selfStudyStudents = selfStudyService.getSelfStudyStudents();

        //then
        assertEquals(1, selfStudyStudents.size());
        assertEquals(MAN, selfStudyStudents.get(0).getGender());
    }

    @Test
    @DisplayName("자습을 신청한 순서대로 자습신청한 학생들 목록이 잘 조회 되나요 ?")
    public void getSelfStudyByCreateDate() {
        //given //when
        selfStudyService.requestSelfStudy(DayOfWeek.MONDAY, 20);
        List<SelfStudyStudentsDto> students = selfStudyService.getSelfStudyStudentsByCreateDate();

        //then
        assertEquals(1, students.size());
        assertEquals(MAN, students.get(0).getGender());
    }

    @Test
    @DisplayName("자습신청한 학생들의 목록이 학년반별 카테고리 목록으로 조회 되나요?")
    public void getSelfStudyStudentsCategoryTest() {
        //given //when
        selfStudyService.requestSelfStudy(DayOfWeek.MONDAY, 20);
        List<SelfStudyStudentsDto> selfStudyStudentsByCategory = selfStudyService.getSelfStudyStudentsByCategory(24L);

        //then
        assertEquals(1, selfStudyStudentsByCategory.size());
        assertEquals(MAN, selfStudyStudentsByCategory.get(0).getGender());
    }

    @Test
    @DisplayName("학생들의 자습신청 상태가 잘 변경되나요?")
    public void updateSelfStudyStatus() {
        //given
        memberRepository.save(
                Member.builder()
                        .memberName("qoxogus1")
                        .stuNum("2410")
                        .password("1234")
                        .email("s20033@gsm.hs.kr")
                        .roles(Collections.singletonList(Role.ROLE_ADMIN))
                        .music(CAN)
                        .selfStudy(APPLIED)
                        .point(0L)
                        .gender(MAN)
                        .build()
        );

        memberRepository.save(
                Member.builder()
                        .memberName("qwer")
                        .stuNum("2408")
                        .password("1234")
                        .email("s20031@gsm.hs.kr")
                        .roles(Collections.singletonList(Role.ROLE_ADMIN))
                        .music(CAN)
                        .selfStudy(CANT)
                        .point(0L)
                        .gender(MAN)
                        .build()
        );

        memberRepository.save(
                Member.builder()
                        .memberName("rewq")
                        .stuNum("2407")
                        .password("1234")
                        .email("s20030@gsm.hs.kr")
                        .roles(Collections.singletonList(Role.ROLE_ADMIN))
                        .music(CAN)
                        .selfStudy(SelfStudy.CAN)
                        .point(0L)
                        .gender(MAN)
                        .build()
        );

        //when
        selfStudyService.updateSelfStudyStatus();

        em.flush();
        em.clear();

        //then
        assertEquals(SelfStudy.CAN, memberRepository.findByEmail("s20033@gsm.hs.kr").get().getSelfStudy());
        assertEquals(SelfStudy.CAN, memberRepository.findByEmail("s20031@gsm.hs.kr").get().getSelfStudy());
        assertEquals(SelfStudy.CAN, memberRepository.findByEmail("s20030@gsm.hs.kr").get().getSelfStudy()); //이 회원은 그대로 CAN
    }

    @Test
    @Order(1)
    @DisplayName("자습신청한 학생 수 카운트가 잘 세지나요?")
    public void selfStudyCountTest() {
        //given //when
        selfStudyService.requestSelfStudy(DayOfWeek.MONDAY, 20);

        Map<String, String> selfStudyInfo = selfStudyService.selfStudyInfo();

        //then
        assertEquals(APPLIED.toString(), selfStudyInfo.get("selfStudy_status"));
        assertEquals("1", selfStudyInfo.get("count"));
    }

    @Test
    @DisplayName("자습신청 금지가 제대로 됬나요?")
    public void banSelfStudyTest() {
        Member currentMember = currentMemberUtil.getCurrentMember();

        // given // when
        selfStudyService.requestSelfStudy(DayOfWeek.MONDAY, 20);
        selfStudyService.banSelfStudy(currentMember.getId());

        // then
        assertEquals(IMPOSSIBLE, currentMember.getSelfStudy());
        assertEquals(LocalDate.now().plusDays(7).toString(), currentMember.getSelfStudyExpiredDate().toString().substring(0, 10));
    }

    @Test
    @DisplayName("자습신청 금지 취소가 잘 되나요?")
    public void banCancelSelfStudyTest() {
        Member currentMember = currentMemberUtil.getCurrentMember();

        //given //when
        selfStudyService.requestSelfStudy(DayOfWeek.MONDAY, 20);
        selfStudyService.banSelfStudy(currentMember.getId());
        selfStudyService.cancelBanSelfStudy(currentMember.getId());

        em.flush();
        em.clear();

        //then
        assertEquals(CAN.toString(), currentMemberUtil.getCurrentMember().getSelfStudy().toString());
        assertEquals(null, currentMemberUtil.getCurrentMember().getSelfStudyExpiredDate());
    }

    @Test
    @DisplayName("자습신청 금지 때 회원을 찾을 수 없음 예외가 잘 터지나요 ?")
    public void banAndBanCancelSelfStudyExceptionTest() {
        assertThrows(
                DotoriException.class,
                () -> selfStudyService.banSelfStudy(0L)
        );
    }
}