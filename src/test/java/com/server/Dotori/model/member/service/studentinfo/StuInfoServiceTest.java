package com.server.Dotori.model.member.service.studentinfo;

import com.server.Dotori.model.member.dto.*;
import com.server.Dotori.model.member.enumType.Role;
import com.server.Dotori.model.member.repository.member.MemberRepository;
import com.server.Dotori.util.CurrentMemberUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class StuInfoServiceTest {

    @Autowired private StuInfoService stuInfoService;
    @Autowired private MemberRepository memberRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private CurrentMemberUtil currentMemberUtil;
    @Autowired private EntityManager em;

    @BeforeEach
    @DisplayName("로그인 되어있는 유저를 확인하는 테스트")
    void currentUser() {
        //given
        MemberDto memberDto = MemberDto.builder()
                .memberName("배태현")
                .stuNum("2409")
                .password("0809")
                .email("s20032@gsm.hs.kr")
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
    @DisplayName("학생정보 전체조회가 잘 되나요?")
    public void findAllStudentInfo() {
        List<StudentInfoDto> allStudentInfo = stuInfoService.getAllStudentInfo();
        assertEquals(1, allStudentInfo.size());
    }

    @Test
    @DisplayName("학년반별로 학생의 정보가 잘 조회되나요?")
    public void getStudentInfoTest() {
        //given //when
        List<StudentInfoDto> studentInfo = stuInfoService.getStudentInfo(24L);

        //then
        assertEquals(1, studentInfo.size());
    }

    @Test
    @DisplayName("권한이 제대로 변경되나요?")
    public void updateRole() {
        //given //when
        stuInfoService.updateRole(
                RoleUpdateDto.builder()
                        .receiverId(currentMemberUtil.getCurrentMember().getId())
                        .roles(Collections.singletonList(Role.ROLE_COUNCILLOR))
                        .build()
        );

        em.flush();
        em.clear();

        //then
        assertEquals(memberRepository.findByEmail("s20032@gsm.hs.kr").get().getRoles().toString(), Collections.singletonList(Role.ROLE_COUNCILLOR).toString());
    }

    @Test
    @DisplayName("학번이 잘 변경되나요?")
    public void updateStuNum() {
        //given //when
        stuInfoService.updateStuNum(
                StuNumUpdateDto.builder()
                        .receiverId(currentMemberUtil.getCurrentMember().getId())
                        .stuNum("1111")
                        .build()
        );

        //then
        assertEquals("1111", memberRepository.findById(currentMemberUtil.getCurrentMember().getId()).get().getStuNum());
    }

    @Test
    @DisplayName("이름이 잘 변경되나요?")
    public void updateMemberNameTest() {
        //given //when
        stuInfoService.updateMemberName(
                MemberNameUpdateDto.builder()
                        .receiverId(currentMemberUtil.getCurrentMember().getId())
                        .memberName("배털")
                        .build()
        );

        //then
        assertNotNull(memberRepository.findByEmail("s20032@gsm.hs.kr"));
    }
}