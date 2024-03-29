package com.server.Dotori.domain.self_study.service;

import com.server.Dotori.domain.member.Member;
import com.server.Dotori.domain.member.dto.MemberDto;
import com.server.Dotori.domain.member.enumType.Gender;
import com.server.Dotori.domain.member.enumType.MusicStatus;
import com.server.Dotori.domain.member.enumType.Role;
import com.server.Dotori.domain.member.enumType.SelfStudyStatus;
import com.server.Dotori.domain.member.repository.member.MemberRepository;
import com.server.Dotori.domain.self_study.SelfStudy;
import com.server.Dotori.domain.self_study.SelfStudyCount;
import com.server.Dotori.domain.self_study.dto.SelfStudyCheckDto;
import com.server.Dotori.domain.self_study.dto.SelfStudyStudentsDto;
import com.server.Dotori.domain.self_study.repository.SelfStudyCountRepository;
import com.server.Dotori.domain.self_study.repository.SelfStudyRepository;
import com.server.Dotori.global.exception.DotoriException;
import com.server.Dotori.global.util.CurrentMemberUtil;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
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
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

@SpringBootTest
@Transactional
@TestMethodOrder(OrderAnnotation.class)
class SelfStudyStatusServiceTest {

    private int THREAD_CNT = 100;
    private ExecutorService ex = Executors.newFixedThreadPool(THREAD_CNT);
    private CountDownLatch latch = new CountDownLatch(THREAD_CNT);

    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private MemberRepository memberRepository;
    @Autowired private CurrentMemberUtil currentMemberUtil;
    @Autowired private SelfStudyService selfStudyService;
    @Autowired private SelfStudyRepository selfStudyRepository;
    @Autowired private SelfStudyCountRepository selfStudyCountRepository;
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
                .gender(Gender.MAN)
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

        assertEquals(SelfStudyStatus.APPLIED, currentMemberUtil.getCurrentMember().getSelfStudyStatus());
        assertEquals(1 , selfStudyRepository.findAll().size());
        assertEquals(1L, selfStudyCountRepository.findSelfStudyCountById(1L).getCount());
    }

    @Test
    @Disabled
    @DisplayName("자습신청 동시성 테스트")
    public void requestSelfStudyMultiThreadTest() throws InterruptedException {
        for (int i = 0; i < THREAD_CNT; i++) {
            ex.execute(() -> {
                selfStudyService.requestSelfStudy(DayOfWeek.MONDAY, 20);
                latch.countDown();
            });
        }

        latch.await();

        Long count = selfStudyCountRepository.findSelfStudyCountById(1L).getCount();

        System.out.println("count = " + count);
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
                () -> selfStudyService.requestSelfStudy(DayOfWeek.SATURDAY, 20)
        );

        assertThrows(
                DotoriException.class,
                () -> selfStudyService.requestSelfStudy(DayOfWeek.SUNDAY, 20)
        );

        assertThrows(
                DotoriException.class,
                () -> selfStudyService.requestSelfStudy(DayOfWeek.MONDAY, 19)
        );
    }

    @Test
    @DisplayName("자습 신청/자습 신청 취소를 할 수 있는 상태가 아닐 때 예외가 제대로 터지나요 ?")
    public void requestSelfStudyStatusExceptionTest() {
        //given
        Member currentMember = currentMemberUtil.getCurrentMember();

        //when
        currentMember.updateSelfStudy(SelfStudyStatus.APPLIED);

        //then
        assertThrows(
                DotoriException.class,
                () -> selfStudyService.requestSelfStudy(DayOfWeek.MONDAY, 20)
        );

        //when
        currentMember.updateSelfStudy(SelfStudyStatus.CAN);

        // then
        assertThrows(
                DotoriException.class,
                () -> selfStudyService.cancelSelfStudy(DayOfWeek.MONDAY, 20)
        );
    }

    @Test
    @DisplayName("유저로부터 자습신청 요청이 두번 들어왔을 때 제대로 예외가 터지나요 ?")
    public void twoRequestSelfStudyExceptionTest() {
        //given
        selfStudyRepository.save(
                SelfStudy.builder()
                .member(currentMemberUtil.getCurrentMember())
                .build()
        );

        //when //then
        assertThrows(
                DataIntegrityViolationException.class,
                () -> selfStudyService.requestSelfStudy(DayOfWeek.MONDAY, 20)
        );
    }
    
    @Test
    @DisplayName("50명 이상이 자습을 신청했을 때 자습을 신청하면 제대로 예외가 터지나요 ?")
    public void fiftyOverSelfStudyExceptionTest() {
        //given
        List<com.server.Dotori.domain.self_study.SelfStudy> selfStudyList = Stream.generate(
                () -> com.server.Dotori.domain.self_study.SelfStudy.builder()
                        .member(null)
                        .build()
        ).limit(50).collect(Collectors.toList());

        selfStudyRepository.saveAll(selfStudyList);

        SelfStudyCount findCount = selfStudyCountRepository.findSelfStudyCountById(1L);

        for (int i = 0; i < 50; i++) {
            findCount.addCount();
        }

        //when //then
        assertThrows(
                DotoriException.class,
                () -> selfStudyService.requestSelfStudy(DayOfWeek.MONDAY, 20)
        );
    }

    @Test
    @DisplayName("자습신청 취소가 제대로 되나요?")
    public void cancelSelfStudy() {
        selfStudyService.requestSelfStudy(DayOfWeek.MONDAY, 20);
        selfStudyService.cancelSelfStudy(DayOfWeek.MONDAY, 20);

        assertEquals(SelfStudyStatus.CANT, currentMemberUtil.getCurrentMember().getSelfStudyStatus());
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
    @DisplayName("자습 신청한 학생 이름으로 잘 검색 되나요?")
    public void getSelfStudyStudentByMemberNameTest() {
        //given //when
        selfStudyService.requestSelfStudy(DayOfWeek.MONDAY, 20);

        List<SelfStudyStudentsDto> findSelfStudyAppliedStudent = selfStudyService.getSelfStudyStudentByMemberName("배태현");

        assertEquals("배태현", findSelfStudyAppliedStudent.get(0).getMemberName());
        assertEquals("2409", findSelfStudyAppliedStudent.get(0).getStuNum());
        assertEquals(Gender.MAN, findSelfStudyAppliedStudent.get(0).getGender());
    }

    @Test
    @DisplayName("자습 신청한 학생 이름으로 검색했을 때 결과가 없다면 예외가 제대로 터지나요 ?")
    public void getSelfStudyStudentByMemberNameExceptionTest() {
        //when //then
        assertThrows(
                DotoriException.class,
                () -> selfStudyService.getSelfStudyStudentByMemberName("없는사람")
        );
    }

    @Test
    @DisplayName("자습 신청한 학생들 목록이 비어있을 때 예외가 잘 터지나요 ?")
    public void selfStudyStudentsListEmptyExceptionTest() {
        //when //then
        assertThrows(
                DotoriException.class,
                () -> selfStudyService.getSelfStudyStudentsByCreateDate()
        );
    }

    @Test
    @DisplayName("자습을 신청한 순서대로 자습신청한 학생들 목록이 잘 조회 되나요 ?")
    public void getSelfStudyByCreateDate() {
        //given //when
        selfStudyService.requestSelfStudy(DayOfWeek.MONDAY, 20);
        List<SelfStudyStudentsDto> students = selfStudyService.getSelfStudyStudentsByCreateDate();

        //then
        assertEquals(1, students.size());
        assertEquals(Gender.MAN, students.get(0).getGender());
    }
    
    @Test
    @DisplayName("자습을 신청한 순서대로 자습신청한 학생들 목록이 비어있을 때 예외가 잘 터지나요 ?")
    public void getSelfStudyByCreateDateExceptionTest() {
        //when //then
        assertThrows(
                DotoriException.class,
                () -> selfStudyService.getSelfStudyStudentsByCreateDate()
        );
    }

    @Test
    @DisplayName("자습신청한 학생들의 목록이 학년반별 카테고리 목록으로 조회 되나요?")
    public void getSelfStudyStudentsCategoryTest() {
        //given //when
        selfStudyService.requestSelfStudy(DayOfWeek.MONDAY, 20);
        List<SelfStudyStudentsDto> selfStudyStudentsByCategory = selfStudyService.getSelfStudyStudentsByCategory(24L);

        //then
        assertEquals(1, selfStudyStudentsByCategory.size());
        assertEquals(Gender.MAN, selfStudyStudentsByCategory.get(0).getGender());
    }
    
    @Test
    @DisplayName("자습신청한 학생을 학년반별로 조회할 때 자습신청한 학생이 없다면 예외가 터지나요 ?")
    public void getClassNumSelfStudyStudentsExceptionTest() {
        //when //then
        assertThrows(
                DotoriException.class,
                () -> selfStudyService.getSelfStudyStudentsByCategory(9L)
        );
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
                        .musicStatus(MusicStatus.CAN)
                        .selfStudyStatus(SelfStudyStatus.APPLIED)
                        .point(0L)
                        .gender(Gender.MAN)
                        .build()
        );

        memberRepository.save(
                Member.builder()
                        .memberName("qwer")
                        .stuNum("2408")
                        .password("1234")
                        .email("s20031@gsm.hs.kr")
                        .roles(Collections.singletonList(Role.ROLE_ADMIN))
                        .musicStatus(MusicStatus.CAN)
                        .selfStudyStatus(SelfStudyStatus.CANT)
                        .point(0L)
                        .gender(Gender.MAN)
                        .build()
        );

        memberRepository.save(
                Member.builder()
                        .memberName("rewq")
                        .stuNum("2407")
                        .password("1234")
                        .email("s20030@gsm.hs.kr")
                        .roles(Collections.singletonList(Role.ROLE_ADMIN))
                        .musicStatus(MusicStatus.CAN)
                        .selfStudyStatus(SelfStudyStatus.CAN)
                        .point(0L)
                        .gender(Gender.MAN)
                        .build()
        );

        //when
        selfStudyService.updateSelfStudyStatus();

        em.flush();
        em.clear();

        //then
        assertEquals(SelfStudyStatus.CAN, memberRepository.findByEmail("s20033@gsm.hs.kr").get().getSelfStudyStatus());
        assertEquals(SelfStudyStatus.CAN, memberRepository.findByEmail("s20031@gsm.hs.kr").get().getSelfStudyStatus());
        assertEquals(SelfStudyStatus.CAN, memberRepository.findByEmail("s20030@gsm.hs.kr").get().getSelfStudyStatus()); //이 회원은 그대로 CAN
    }

    @Test
    @Order(1)
    @DisplayName("자습신청한 학생 수 카운트가 잘 세지나요?")
    public void selfStudyCountTest() {
        //given //when
        selfStudyService.requestSelfStudy(DayOfWeek.MONDAY, 20);

        Map<String, String> selfStudyInfo = selfStudyService.selfStudyInfo();

        //then
        assertEquals(SelfStudyStatus.APPLIED.toString(), selfStudyInfo.get("selfStudy_status"));
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
        assertEquals(SelfStudyStatus.IMPOSSIBLE, currentMember.getSelfStudyStatus());
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
        assertEquals(SelfStudyStatus.CAN.toString(), currentMemberUtil.getCurrentMember().getSelfStudyStatus().toString());
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

    @Test
    @DisplayName("자습 상태가 true로 변경이 잘되나요?")
    public void changeTrueSelfStudyCheck(){
        Member member = currentMemberUtil.getCurrentMember();

        //given
        SelfStudyCheckDto selfStudyCheckDto = new SelfStudyCheckDto(true);

        //when
        selfStudyService.checkSelfStudy(member.getId(), selfStudyCheckDto);

        //then
        assertEquals(member.getSelfStudyCheck(), true);
    }

    @Test
    @DisplayName("자습 상태가 false로 변경이 잘되나요?")
    public void changeFalseSelfStudyCheck(){
        Member member = currentMemberUtil.getCurrentMember();
        member.updateSelfStudyCheck(true);

        SelfStudyCheckDto selfStudyCheckDto = new SelfStudyCheckDto(false);

        selfStudyService.checkSelfStudy(member.getId(), selfStudyCheckDto);

        assertEquals(member.getSelfStudyCheck(), false);
    }
}