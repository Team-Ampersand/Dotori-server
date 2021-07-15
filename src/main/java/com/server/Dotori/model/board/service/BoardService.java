package com.server.Dotori.model.board.service;

import com.server.Dotori.model.board.dto.BoardDto;
import com.server.Dotori.model.board.dto.BoardResponseDto;

public interface BoardService {

    void createBoard(BoardDto boardDto);

    BoardResponseDto readBoardById(Long id);


}
