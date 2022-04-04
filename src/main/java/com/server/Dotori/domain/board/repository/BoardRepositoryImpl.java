package com.server.Dotori.domain.board.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.server.Dotori.domain.board.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.server.Dotori.domain.board.QBoard.board;
import static com.server.Dotori.domain.member.QMember.member;

@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Board> getAllBoardCreateDateDesc(Pageable pageable) {
        QueryResults<Board> boardQueryResults = queryFactory
                .select(board)
                .from(board)
                .innerJoin(board.member, member)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(board.createdDate.desc())
                .fetchResults();

        List<Board> content = boardQueryResults.getResults();

        long total = boardQueryResults.getTotal();

        return new PageImpl<>(content, pageable, total);
    }
}
