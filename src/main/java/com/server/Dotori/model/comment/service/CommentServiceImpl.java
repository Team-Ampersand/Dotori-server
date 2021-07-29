package com.server.Dotori.model.comment.service;

import com.server.Dotori.exception.board.exception.BoardNotFoundException;
import com.server.Dotori.model.board.Board;
import com.server.Dotori.model.board.repository.BoardRepository;
import com.server.Dotori.model.comment.Comment;
import com.server.Dotori.model.comment.dto.CommentDto;
import com.server.Dotori.model.comment.repository.CommentRepository;
import com.server.Dotori.model.member.Member;
import com.server.Dotori.model.member.repository.MemberRepository;
import com.server.Dotori.util.CurrentUserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final CurrentUserUtil currentUserUtil;

    @Override
    public Long createComment(Long boardId, CommentDto commentDto) {

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException());

        Member currentUser = currentUserUtil.getCurrentUser();

        Comment comment = commentRepository.save(commentDto.saveToEntity(board, currentUser));
        return comment.getId();
    }
}
