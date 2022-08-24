package com.server.Dotori.domain.board.repository;

import com.server.Dotori.domain.board.BoardImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardImageRepository extends JpaRepository<BoardImage,Long> {
    List<BoardImage> getBoardImageByBoardId(Long boardId);
    void deleteByBoardId(Long boardId);
}