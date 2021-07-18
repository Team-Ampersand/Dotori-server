package com.server.Dotori.model.board.service;

import com.server.Dotori.model.board.Board;
import com.server.Dotori.model.board.dto.BoardAllResponseDto;
import com.server.Dotori.model.board.dto.BoardDto;
import com.server.Dotori.model.board.dto.BoardResponseDto;
import com.server.Dotori.model.board.repository.BoardRepository;
import com.server.Dotori.model.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BoardServiceTest {

    @Autowired private MemberRepository memberRepository;
    @Autowired private BoardRepository boardRepository;
    @Autowired private BoardService boardService;

    @Test
    @DisplayName("게시글 작성 테스트")
    public void createBoardTest() {

        //given
        BoardDto boardDto = BoardDto.builder()
                .title("Dotori 긴급 점검")
                .content("배태현 개발자님이 점검중입니다.")
                .build();

        //when
        Long boardIdx = boardService.createBoard(boardDto);
        Optional<Board> findBoard = boardRepository.findById(boardIdx);

        //then
        assertThat(boardIdx).isEqualTo(1);
        assertThat(findBoard.get().getTitle()).isEqualTo("Dotori 긴급 점검");
    }
//
//    @BeforeEach
//    public void before() {
//        BoardDto boardDto = BoardDto.builder()
//                .title("Dotori 게시글 조회 점검")
//                .content("배태현 개발자님이 점검중입니다.")
//                .build();
//        boardService.createBoard(boardDto);
//    }
//
//    //회원로직이 어느정도 작성되면 통합테스트를 진행해야할 듯 함
//    @Test
//    @DisplayName("id로 게시물 조회 테스트")
//    public void readByIdBoardTest() {
//        //given when
//        long id = 1;
//        BoardResponseDto boardResponseDto = boardService.readBoardById(id);
//
//        //then
//        assertThat(boardResponseDto.getTitle()).isEqualTo("Dotori 게시글 조회 점검");
//    }
//
//    @Test
//    @DisplayName("게시물 모두 조회 테스트")
//    public void readAllBoardTest() {
//        //given
//
//        //when
//
//        //then
//
//    }
}