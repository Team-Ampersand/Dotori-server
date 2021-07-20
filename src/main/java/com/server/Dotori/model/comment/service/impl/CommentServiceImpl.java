package com.server.Dotori.model.comment.service.impl;

import com.server.Dotori.exception.board.exception.BoardNotFoundException;
import com.server.Dotori.model.board.Board;
import com.server.Dotori.model.board.repository.BoardRepository;
import com.server.Dotori.model.comment.dto.CommentDto;
import com.server.Dotori.model.comment.repository.CommentRepository;
import com.server.Dotori.model.comment.service.CommentService;
import com.server.Dotori.model.member.Member;
import com.server.Dotori.model.member.enumType.Role;
import com.server.Dotori.model.member.repository.MemberRepository;
import com.server.Dotori.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public Long saveComment(Long id, CommentDto commentDto, HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        String username = jwtTokenProvider.getUsername(token);
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new BoardNotFoundException());
        Member findUser = memberRepository.findByUsername(username);
        //댓글 작성자 이름 옆에 권한 나타내주면 좋아보임
        commentDto.setBoard(board);
        commentDto.setMember(findUser);
        commentRepository.save(commentDto.toEntity(findUser.getUsername()));
        return board.getId();
    }
}
