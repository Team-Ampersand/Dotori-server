package com.server.Dotori.model.board.service;

import com.server.Dotori.model.board.Board;
import com.server.Dotori.model.board.dto.BoardGetDto;
import com.server.Dotori.model.board.dto.BoardSaveDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardService {

    Board createBoard(BoardSaveDto boardSaveDto);

    Page<BoardGetDto> getAllBoard(Pageable pageable);
}
