package com.server.Dotori.domain.board.repository;

import com.server.Dotori.domain.board.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardRepositoryCustom {

    Page<Board> getAllBoardCreateDateDesc(Pageable pageable);
}
