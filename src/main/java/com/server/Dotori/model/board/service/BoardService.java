package com.server.Dotori.model.board.service;

import com.server.Dotori.model.board.dto.BoardAllResponseDto;
import com.server.Dotori.model.board.dto.BoardDto;
import com.server.Dotori.model.board.dto.BoardResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardService {

    Long createBoard(BoardDto boardDto);

    BoardResponseDto readBoardById(Long id);

    Page<BoardAllResponseDto> readAllBoard(Pageable pageable);

    Long updateBoard(Long id, BoardDto boardDto);

    Long deleteBoard(Long id);
}
