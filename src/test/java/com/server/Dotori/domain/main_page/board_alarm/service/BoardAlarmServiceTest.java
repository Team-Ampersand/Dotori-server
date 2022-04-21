package com.server.Dotori.domain.main_page.board_alarm.service;

import com.server.Dotori.domain.board.dto.BoardDto;
import com.server.Dotori.domain.board.service.BoardService;
import com.server.Dotori.domain.main_page.dto.BoardAlarmDto;
import com.server.Dotori.domain.main_page.service.BoardAlarmService;
import com.server.Dotori.domain.member.dto.MemberDto;
import com.server.Dotori.domain.member.enumType.Gender;
import com.server.Dotori.domain.member.repository.member.MemberRepository;
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

import java.time.LocalDate;
import java.util.List;

import static com.server.Dotori.domain.member.enumType.Role.ROLE_ADMIN;
import static com.server.Dotori.domain.member.enumType.Role.ROLE_MEMBER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@SpringBootTest
class BoardAlarmServiceTest {

    @Autowired private MemberRepository memberRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private BoardService boardService;
    @Autowired private BoardAlarmService boardAlarmService;

    @BeforeEach
    @DisplayName("로그인 되어있는 유저를 세팅하는 테스트")
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
                List.of(new SimpleGrantedAuthority(ROLE_ADMIN.name())));
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(token);
        System.out.println("=================================");
        System.out.println(context);

        //then
        String currentMemberEmail = CurrentMemberUtil.getCurrentEmail();
        assertEquals("s20032@gsm.hs.kr", currentMemberEmail);
    }

    @Test
    @DisplayName("가장 마지막에 등록된 공지사항이 잘 가져와지나요 ?")
    public void boardAlarmTest() {
        // given // when
        boardService.createBoard(
                BoardDto.builder()
                        .title("도토리 공지사항")
                        .content("도토리 공지사항 생성 테스트")
                        .build(), null
        );

        BoardAlarmDto boardAlarmInfo = boardAlarmService.getBoardAlarmInfo();

        // then
        assertEquals("도토리 공지사항", boardAlarmInfo.getTitle());
        assertEquals(ROLE_MEMBER.name(), boardAlarmInfo.getWriterRole().get(0).name());
        assertEquals(LocalDate.now(), boardAlarmInfo.getLastBoardWriteDate());
    }

    @Test
    @DisplayName("가장 마지막에 등록된 공지사항 즉, 공지사항들이 비어있을 때 예외가 터지나요 ?")
    public void boardAlarmExceptionTest() {
        //when //then
        assertThrows(
                DotoriException.class,
                () -> boardAlarmService.getBoardAlarmInfo()
        );
    }
}