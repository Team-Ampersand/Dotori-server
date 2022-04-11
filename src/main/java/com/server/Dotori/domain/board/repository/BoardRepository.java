package com.server.Dotori.domain.board.repository;

import com.server.Dotori.domain.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {
    Board findTop1ByOrderByCreatedDateDesc(); // 제일 마지막에 등록된 공지사항을 불러온다.
}
