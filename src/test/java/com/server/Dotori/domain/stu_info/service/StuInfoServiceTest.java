package com.server.Dotori.domain.stu_info.service;

import com.server.Dotori.domain.member.dto.MemberDto;
import com.server.Dotori.domain.member.enumType.Gender;
import com.server.Dotori.domain.member.enumType.Role;
import com.server.Dotori.domain.member.repository.member.MemberRepository;
import com.server.Dotori.domain.stu_info.dto.*;
import com.server.Dotori.global.exception.DotoriException;
import com.server.Dotori.global.util.CurrentMemberUtil;
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
    @DisplayName("해당 반에 해당하는 학생정보가 없을 때 예외가 터지나요 ?")
    public void findAllStudentInfoExceptionTest() {
        //when //then
        assertThrows(
                DotoriException.class,
                () -> stuInfoService.getStudentInfo(0L)
        );
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
    @DisplayName("권한을 변경할 때 회원을 찾지 못하면 예외가 터지나요 ?")
    public void updateRoleExceptionTest() {
        //when //then
        assertThrows(
                DotoriException.class,
                () -> stuInfoService.updateRole(
                        RoleUpdateDto.builder()
                        .receiverId(0L)
                        .roles(Collections.singletonList(Role.ROLE_COUNCILLOR))
                        .build()
                )
        );
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
    @DisplayName("학번을 변경할 때 회원의 id로 회원을 찾지 못했을 때 예외가 터지나요 ?")
    public void updateStuNumException1Test() {
        //when //then
        assertThrows(
                DotoriException.class,
                () -> stuInfoService.updateStuNum(
                        StuNumUpdateDto.builder()
                                .receiverId(0L)
                                .stuNum("1111")
                                .build()
                )
        );
    }
    
    @Test
    @DisplayName("학번을 변경할 때 해당 학번에 해당하는 학생이 이미 있으면 예외가 터지나요 ?")
    public void updateStuNumException2Test() {
        //when //then
        assertThrows(
                DotoriException.class,
                () -> stuInfoService.updateStuNum(
                        StuNumUpdateDto.builder()
                                .receiverId(currentMemberUtil.getCurrentMember().getId())
                                .stuNum("2409")
                                .build()
                )
        );
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

    @Test
    @DisplayName("학생의 성별이 잘 변경되나요 ?")
    public void updateGenderTest() {
        //given //when
        stuInfoService.updateGender(
                GenderUpdateDto.builder()
                        .receiverId(currentMemberUtil.getCurrentMember().getId())
                        .gender(Gender.WOMAN)
                        .build()
        );

        //then
        assertEquals(Gender.WOMAN, memberRepository.findByEmail("s20032@gsm.hs.kr").get().getGender());
    }

    @Test
    @DisplayName("이름으로 학생정보가 잘 검색되나요?")
    public void searchMemberByMemberNameTest() {
        //given //when
        List<StudentInfoDto> stuInfoByMemberName = stuInfoService.getStuInfoByMemberName("배태현");

        //then
        assertEquals("배태현", stuInfoByMemberName.get(0).getMemberName());
    }

    @Test
    @DisplayName("이름으로 학생정보를 검색했을 때 결과가 없다면 예외가 터지나요 ?")
    public void searchMemberByMemberNameExceptionTest() {
        //when //then
        assertThrows(
                DotoriException.class,
                () -> stuInfoService.getStuInfoByMemberName("없는사람")
        );
    }
}