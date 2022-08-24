package com.server.Dotori.domain.board.service;

import com.server.Dotori.domain.board.Board;
import com.server.Dotori.domain.board.dto.BoardDto;
import com.server.Dotori.domain.board.dto.BoardGetDto;
import com.server.Dotori.domain.board.dto.BoardGetIdDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BoardService {

    Board createBoard(BoardDto boardDto, List<MultipartFile> multipartFileList);

    Page<BoardGetDto> getAllBoard(Pageable pageable);

    BoardGetIdDto getBoardById(Long boardId);

    Board updateBoard(Long boardId, BoardDto boardUpdateDto);

    void deleteBoard(Long boardId);
}
