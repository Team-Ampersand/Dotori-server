package com.server.Dotori.model.member.service.point;

import com.server.Dotori.model.member.Member;
import com.server.Dotori.model.member.dto.MemberDto;
import com.server.Dotori.model.member.dto.GetAboutPointDto;
import com.server.Dotori.model.member.enumType.Role;
import com.server.Dotori.model.member.enumType.SelfStudy;
import com.server.Dotori.model.member.repository.MemberRepository;
import com.server.Dotori.model.member.dto.PointDto;
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

import static com.server.Dotori.model.member.enumType.Music.CAN;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PointServiceTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private PointService pointService;
    @Autowired
    private EntityManager em;

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
    @DisplayName("상벌점이 제대로 잘 부여되나요?")
    public void pointTest() {
        //given
        memberRepository.save(
                Member.builder()
                        .username("qoxoqoxo")
                        .stdNum("2420")
                        .password("1234")
                        .email("s20043@gsm.hs.kr")
                        .roles(Collections.singletonList(Role.ROLE_MEMBER))
                        .music(CAN)
                        .selfStudy(SelfStudy.CAN)
                        .point(0L)
                        .answer("배털")
                        .build()
        );

        //when
        pointService.point(
                PointDto.builder()
                        .receiverId(memberRepository.findByUsername("qoxoqoxo").getId())
                        .point(-3L)
                        .build()
        );

        em.flush();
        em.clear();

        //then
        assertEquals(-3L, memberRepository.findByUsername("qoxoqoxo").getPoint());
    }

    @Test
    @DisplayName("반별로 학생들의 상벌점이 조회되나요?")
    public void getAllStudentsPointTest() {
        memberRepository.save(
                Member.builder()
                        .username("qoxoqoxo")
                        .stdNum("1120")
                        .password("1234")
                        .email("s20013@gsm.hs.kr")
                        .roles(Collections.singletonList(Role.ROLE_MEMBER))
                        .music(CAN)
                        .selfStudy(SelfStudy.CAN)
                        .point(-2L)
                        .answer("배털")
                        .build()
        );

        memberRepository.save(
                Member.builder()
                        .username("qoxoqoxo")
                        .stdNum("1119")
                        .password("1234")
                        .email("s20083@gsm.hs.kr")
                        .roles(Collections.singletonList(Role.ROLE_MEMBER))
                        .music(CAN)
                        .selfStudy(SelfStudy.CAN)
                        .point(-13L)
                        .answer("배털")
                        .build()
        );

        //when
        List<GetAboutPointDto> allStudentPoint = pointService.getAllStudentPoint(11L);

        //then
        assertNotNull(allStudentPoint);
    }
}