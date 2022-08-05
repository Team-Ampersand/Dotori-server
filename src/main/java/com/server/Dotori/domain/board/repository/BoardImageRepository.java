package com.server.Dotori.domain.board.repository;

import com.server.Dotori.domain.board.BoardImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardImageRepository extends JpaRepository<BoardImage,Long> {
    String findBoardImageByBoardId(Long boardId);
}
