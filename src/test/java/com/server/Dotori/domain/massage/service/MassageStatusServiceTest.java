package com.server.Dotori.domain.massage.service;

import com.server.Dotori.domain.massage.dto.MassageStudentsDto;
import com.server.Dotori.domain.massage.repository.MassageRepository;
import com.server.Dotori.domain.member.dto.MemberDto;
import com.server.Dotori.domain.member.enumType.Gender;
import com.server.Dotori.domain.member.enumType.MassageStatus;
import com.server.Dotori.domain.member.enumType.Role;
import com.server.Dotori.domain.member.repository.member.MemberRepository;
import com.server.Dotori.global.exception.DotoriException;
import com.server.Dotori.global.util.CurrentMemberUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class MassageStatusServiceTest {

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
        assertEquals("s20014@gsm.hs.kr", currentMemberEmail);
    }

    @Test
    @DisplayName("안마의자 신청이 잘 되나요?")
    public void requestMassageTest() {
        massageService.requestMassage(DayOfWeek.WEDNESDAY, 20, 40);

        assertThat(MassageStatus.APPLIED).isEqualTo(currentMemberUtil.getCurrentMember().getMassageStatus());
        assertThat(1).isEqualTo(massageRepository.count());
    }

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
    @DisplayName("안마의자 신청 취소가 잘 되는지 검증")
    public void cancelMassageTest() {
        massageService.requestMassage(DayOfWeek.THURSDAY,20,30);
        massageService.cancelMassage(20, 31);

        assertEquals(MassageStatus.CANT, currentMemberUtil.getCurrentMember().getMassageStatus());
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
        Map<String, String> find = massageService.getMassageInfo();

        assertEquals(String.valueOf(MassageStatus.APPLIED), find.get("status"));
        assertEquals("1", find.get("count"));

    }
}
