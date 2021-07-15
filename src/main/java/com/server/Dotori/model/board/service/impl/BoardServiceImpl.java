package com.server.Dotori.model.board.service.impl;

import com.server.Dotori.exception.board.exception.BoardNotFoundException;
import com.server.Dotori.model.board.Board;
import com.server.Dotori.model.board.dto.BoardDto;
import com.server.Dotori.model.board.dto.BoardResponseDto;
import com.server.Dotori.model.board.repository.BoardRepository;
import com.server.Dotori.model.board.service.BoardService;
import com.server.Dotori.model.member.Member;
import com.server.Dotori.model.member.dto.MemberDto;
import com.server.Dotori.model.member.enumType.Role;
import com.server.Dotori.model.member.repository.MemberRepository;
import com.server.Dotori.util.CurrentUserUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final CurrentUserUtil currentUserUtil;

    @Override
    public void createBoard(BoardDto boardDto) {
        // 유저 로직이 완성되면 request로 token을 받아 이름을 추출해서 member에 findByUsername 넣어줌
        MemberDto memberDto = MemberDto.builder()
                .username("배태현")
                .email("123@naver.com")
                .password("1234")
                .stdNum("1234")
                .build();
        Member member = memberRepository.save(memberDto.toEntity());

        boardDto.setMember(member);
        boardRepository.save(boardDto.toEntity());
    }

    @Override
    public BoardResponseDto readBoardById(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new BoardNotFoundException());

        List<Role> roles = board.getMember().getRoles();
        ModelMapper modelMapper = new ModelMapper();
        BoardResponseDto map = modelMapper.map(board, BoardResponseDto.class);
        map.setRoles(roles);
        return map;
    }

}
