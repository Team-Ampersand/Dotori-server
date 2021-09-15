package com.server.Dotori.model.member.service.studentinfo;

import com.server.Dotori.model.member.dto.MemberDto;
import com.server.Dotori.model.member.dto.RoleUpdateDto;
import com.server.Dotori.model.member.dto.StuNumUpdateDto;
import com.server.Dotori.model.member.dto.StudentInfoDto;
import com.server.Dotori.model.member.enumType.Role;
import com.server.Dotori.model.member.repository.MemberRepository;
import com.server.Dotori.util.CurrentUserUtil;
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
    @Autowired private CurrentUserUtil currentUserUtil;
    @Autowired private EntityManager em;

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
        memberRepository.save(memberDto.toEntity());
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
                        .receiverId(currentUserUtil.getCurrentUser().getId())
                        .roles(Collections.singletonList(Role.ROLE_COUNCILLOR))
                        .build()
        );

        em.flush();
        em.clear();

        //then
        assertEquals(memberRepository.findByUsername("배태현").getRoles().toString(), Collections.singletonList(Role.ROLE_COUNCILLOR).toString());
    }

    @Test
    @DisplayName("학번이 잘 변경되나요?")
    public void updateStuNum() {
        //given //when
        stuInfoService.updateStuNum(
                StuNumUpdateDto.builder()
                        .receiverId(currentUserUtil.getCurrentUser().getId())
                        .stuNum("1111")
                        .build()
        );

        //then
        assertEquals("1111", memberRepository.findById(currentUserUtil.getCurrentUser().getId()).get().getStdNum());
    }
}