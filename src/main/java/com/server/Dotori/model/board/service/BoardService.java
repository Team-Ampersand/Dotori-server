package com.server.Dotori.model.board.service;

import com.server.Dotori.model.board.Board;
import com.server.Dotori.model.board.dto.BoardDto;
import com.server.Dotori.model.board.dto.BoardGetDto;
import com.server.Dotori.model.board.dto.BoardGetIdDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface BoardService {

    Board createBoard(BoardDto boardDto, MultipartFile multipartFileList);

    Page<BoardGetDto> getAllBoard(Pageable pageable);

    BoardGetIdDto getBoardById(Long boardId);

    Board updateBoard(Long boardId, BoardDto boardUpdateDto);

    void deleteBoard(Long boardId);

    Long countBoard();
}
