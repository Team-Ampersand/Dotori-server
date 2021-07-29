package com.server.Dotori.model.board.service;

import com.server.Dotori.model.board.Board;
import com.server.Dotori.model.board.dto.BoardGetDto;
import com.server.Dotori.model.board.dto.BoardGetIdDto;
import com.server.Dotori.model.board.dto.BoardDto;
import com.server.Dotori.model.board.repository.BoardRepository;
import com.server.Dotori.model.member.dto.MemberDto;
import com.server.Dotori.model.member.enumType.Role;
import com.server.Dotori.model.member.repository.MemberRepository;
import com.server.Dotori.util.CurrentUserUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BoardServiceTest {

    @Autowired
    private BoardService boardService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    @DisplayName("로그인 되어있는 유저를 확인하는 테스트")
    void currentUser() {
        //given
        MemberDto memberDto = MemberDto.builder()
                .username("배태현")
                .stdNum("2409")
                .password("0809")
                .email("s20032@gsm.hs.kr")
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
    @DisplayName("공지사항 생성 테스트")
    public void createBoardTest() {
        //given //when
        Board board = boardService.createBoard(
                BoardDto.builder()
                        .title("도토리 공지사항")
                        .content("도토리 공지사항 생성 테스트")
                        .build()
        );

        //then
        assertTrue(board.getId() != null);
    }

    @Test
    @DisplayName("모든 공지사항 조회")
    public void getAllBoardTest() {
        //given
        Pageable pageable = Pageable.ofSize(5);

        String currentUsername = CurrentUserUtil.getCurrentUserNickname();

        List<Board> boardList = Stream.generate(
                () -> Board.builder()
                        .title("도토리 공지사항")
                        .content("도토리 공지사항 전체조회 테스트")
                        .member(memberRepository.findByUsername(currentUsername))
                        .build()
        ).limit(30).collect(Collectors.toList());
        boardRepository.saveAll(boardList);

        //when
        Page<BoardGetDto> pageBoard = boardService.getAllBoard(pageable);

        //then
        assertThat(pageBoard.getSize() == 30);
    }

    @Test
    @DisplayName("공지사항 id로 공지사항 조회")
    public void getBoardByIdTest() {
        //given
        Board board = boardService.createBoard(
                BoardDto.builder()
                        .title("도토리 공지사항")
                        .content("도토리 공지사항 id로 공지사항 조회 테스트")
                        .build()
        );

        //when
        BoardGetIdDto findBoard = boardService.getBoardById(board.getId());

        //then
        assertThat(findBoard.getId()).isEqualTo(board.getId());
    }

    @Test
    @DisplayName("공지사항 수정 테스트")
    public void updateBoardTest() {
        //given
        Board board = boardService.createBoard(
                BoardDto.builder()
                        .title("도토리 공지사항")
                        .content("도토리 공지사항 수정 테스트")
                        .build()
        );

        //when
        boardService.updateBoard(board.getId(),
                    BoardDto.builder()
                            .title("수정함")
                            .content("수정함")
                            .build()
                );

        //then
        assertThat(board.getTitle()).isEqualTo("수정함");
        assertThat(board.getContent()).isEqualTo("수정함");
    }

    @Test
    @DisplayName("공지사항 삭제 테스트")
    public void deleteBoardTest() {
        //given
        Board board = boardService.createBoard(
                BoardDto.builder()
                        .title("도토리 공지사항")
                        .content("도토리 공지사항 삭제 테스트")
                        .build()
        );

        //when
        boardService.deleteBoard(board.getId());

        //then
        List<Board> findAll = boardRepository.findAll();
        assertTrue(findAll.size() == 0);
    }
}