package com.server.Dotori.model.board.service;

import com.server.Dotori.model.board.Board;
import com.server.Dotori.model.board.dto.BoardDto;
import com.server.Dotori.model.board.repository.BoardRepository;
import com.server.Dotori.model.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
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

    @Test
    @DisplayName("id로 게시물 조회 테스트")
    public void readByIdBoardTest() {
        //given

        //when

        //then

    }
}