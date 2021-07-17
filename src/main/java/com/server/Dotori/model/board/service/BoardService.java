package com.server.Dotori.model.board.service;

import com.server.Dotori.model.board.dto.BoardAllResponseDto;
import com.server.Dotori.model.board.dto.BoardDto;
import com.server.Dotori.model.board.dto.BoardResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardService {

    void createBoard(BoardDto boardDto);

    BoardResponseDto readBoardById(Long id);

    Page<BoardAllResponseDto> readAllBoard(Pageable pageable);

    void updateBoard(Long id, BoardDto boardDto);

    void deleteBoard(Long id);
}
