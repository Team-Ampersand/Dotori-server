package com.server.Dotori.domain.massage.service;

import com.server.Dotori.domain.massage.dto.MassageStudentsDto;
import com.server.Dotori.domain.massage.repository.MassageRepository;
import com.server.Dotori.domain.massage.service.MassageService;
import com.server.Dotori.domain.member.dto.MemberDto;
import com.server.Dotori.domain.member.enumType.Massage;
import com.server.Dotori.domain.member.enumType.Role;
import com.server.Dotori.domain.member.repository.member.MemberRepository;
import com.server.Dotori.global.exception.DotoriException;
import com.server.Dotori.global.util.CurrentMemberUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.scheduling.config.ScheduledTaskHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class MassageServiceTest {

    @Autowired MemberRepository memberRepository;
    @Autowired PasswordEncoder passwordEncoder;
    @Autowired CurrentMemberUtil currentMemberUtil;
    @Autowired MassageService massageService;
    @Autowired MassageRepository massageRepository;
    @Autowired ScheduledTaskHolder scheduledTaskHolder;

    @BeforeEach
    void signupMember() {
        MemberDto memberDto = MemberDto.builder()
                .memberName("김태민")
                .stuNum("2406")
                .password("1234")
                .email("s20014@gsm.hs.kr")
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
        assertEquals("s20014@gsm.hs.kr", currentMemberEmail);
    }

    @Test
    @DisplayName("안마의자 신청이 잘 되나요?")
    public void requestMassageTest() {
        massageService.requestMassage(DayOfWeek.WEDNESDAY, 20, 40);

        assertThat(Massage.APPLIED).isEqualTo(currentMemberUtil.getCurrentMember().getMassage());
        assertThat(1).isEqualTo(massageRepository.count());
    }

    @Disabled
    @Test
    @DisplayName("안마의자 신청 예외가 제대로 터지나요?")
    public void massageExceptionTest() {
        assertThrows(
                DotoriException.class,
                () -> massageService.requestMassage(DayOfWeek.FRIDAY, 20, 20));

        assertThrows(
                DotoriException.class,
                () -> massageService.requestMassage(DayOfWeek.MONDAY, 19, 19));
    }

    @Test
    @DisplayName("새벽 2시에 안마의자 신청 상태가 변경되는 스케쥴러가 동작하는지 확인하는 테스트")
    public void everyNightMassageStatusChangeTest() {
        Set<ScheduledTask> scheduledTasks = scheduledTaskHolder.getScheduledTasks();
        scheduledTasks.forEach(scheduledTask -> scheduledTask.getTask().getRunnable().getClass().getDeclaredMethods());

        long count = scheduledTasks.stream()
                .filter(scheduledTask -> scheduledTask.getTask() instanceof CronTask)
                .map(scheduledTask -> (CronTask) scheduledTask.getTask())
                .filter(cronTask -> cronTask.getExpression().equals("0 0 2 ? * MON-FRI") && cronTask.toString().equals("com.server.Dotori.model.massage.schedule.MassageSchedule.updateMassageStatus"))
                .count();
        Assertions.assertThat(count).isEqualTo(1L);
    }

    @Test
    @DisplayName("안마의자 신청 취소가 잘 되는지 검증")
    public void cancelMassageTest() {
        massageService.requestMassage(DayOfWeek.THURSDAY,20,30);
        massageService.cancelMassage(DayOfWeek.THURSDAY, 20, 31);

        assertEquals(Massage.CANT, currentMemberUtil.getCurrentMember().getMassage());
        assertEquals(0, massageRepository.count());
    }

    @Test
    @DisplayName("안마의자를 신청한 학생 전체조회가 잘 작동하나요?")
    public void findAllMassageTest() {
        massageService.requestMassage(DayOfWeek.THURSDAY, 20, 40);

        List<MassageStudentsDto> massageStudents = massageService.getMassageStudents();

        assertEquals(1, massageStudents.size());
    }

    @Test
    @DisplayName("안마의자를 신청한 학생의 상태와 안마의자 신청 카운트 조회가 잘 되나요?")
    public void findMassageStatusAndCountTest() {
        massageService.requestMassage(DayOfWeek.THURSDAY, 20, 40);
        Map<String, String> find = massageService.getMassageStatusAndCount();

        assertEquals(String.valueOf(Massage.APPLIED), find.get("status"));
        assertEquals("1", find.get("count"));

    }
}
