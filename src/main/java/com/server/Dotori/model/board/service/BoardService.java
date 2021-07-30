package com.server.Dotori.model.board.service;

import com.querydsl.core.Tuple;
import com.server.Dotori.model.board.Board;
import com.server.Dotori.model.board.dto.BoardGetDto;
import com.server.Dotori.model.board.dto.BoardDto;
import com.server.Dotori.model.board.dto.BoardGetIdDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardService {

    Board createBoard(BoardDto boardDto);

    Page<BoardGetDto> getAllBoard(Pageable pageable);

    BoardGetIdDto getBoardById(Long boardId);

    Board updateBoard(Long boardId, BoardDto boardUpdateDto);

    void deleteBoard(Long boardId);
}
