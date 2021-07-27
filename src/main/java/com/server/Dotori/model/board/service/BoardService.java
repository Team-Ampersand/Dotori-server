package com.server.Dotori.model.board.service;

import com.server.Dotori.model.board.Board;
import com.server.Dotori.model.board.dto.BoardSaveDto;

public interface BoardService {

    Board createBoard(BoardSaveDto boardSaveDto);
}
