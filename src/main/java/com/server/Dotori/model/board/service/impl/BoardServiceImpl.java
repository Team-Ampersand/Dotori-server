package com.server.Dotori.model.board.service.impl;

import com.server.Dotori.exception.board.exception.BoardNotFoundException;
import com.server.Dotori.exception.board.exception.BoardNotHavePermissionToCreate;
import com.server.Dotori.exception.board.exception.BoardNotHavePermissionToDelete;
import com.server.Dotori.exception.board.exception.BoardNotHavePermissionToModify;
import com.server.Dotori.model.board.Board;
import com.server.Dotori.model.board.dto.BoardAllResponseDto;
import com.server.Dotori.model.board.dto.BoardDto;
import com.server.Dotori.model.board.dto.BoardResponseDto;
import com.server.Dotori.model.board.repository.BoardRepository;
import com.server.Dotori.model.board.service.BoardService;
import com.server.Dotori.model.member.Member;
import com.server.Dotori.model.member.dto.MemberDto;
import com.server.Dotori.model.member.enumType.Role;
import com.server.Dotori.model.member.repository.MemberRepository;
import com.server.Dotori.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

import static com.server.Dotori.model.member.enumType.Role.ROLE_MEMBER;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public Long createBoard(BoardDto boardDto, HttpServletRequest request) {
        //생성할 때 한번 더 검증해준다
        //생성하기 전 findBy할 수 없으니까
        String token = jwtTokenProvider.resolveToken(request);
        String username = jwtTokenProvider.getUsername(token);
        Member findUser = memberRepository.findByUsername(username);

        if (findUser.getRoles().toString().equals(Collections.singletonList(ROLE_MEMBER).toString())) throw new BoardNotHavePermissionToCreate();

        boardDto.setMember(findUser);
        Board board = boardRepository.save(boardDto.toEntity());
        // 토큰에서 꺼낸 username으로 findByUsername해서 나온 엔티티에서 roles 가져와서 넘기기
        return board.getId();
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

    @Override
    public Page<BoardAllResponseDto> readAllBoard(Pageable pageable) {
        Page<Board> boards = boardRepository.findAll(pageable);

        return boards.map(board -> {
            List<Role> roles = board.getMember().getRoles();
            ModelMapper mapper = new ModelMapper();
            BoardAllResponseDto map = mapper.map(board, BoardAllResponseDto.class);
            map.setRoles(roles);
            return map;
        });
    }

    @Override
    @Transactional
    public Long updateBoard(Long id, BoardDto boardDto) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new BoardNotFoundException());

        if (board.getMember().getRoles().toString().equals(Collections.singletonList(ROLE_MEMBER).toString())) throw new BoardNotHavePermissionToModify();

        board.updateBoard(boardDto.getTitle(), boardDto.getContent());
        return board.getId();
    }

    @Override
    public Long deleteBoard(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new BoardNotFoundException());

        if (board.getMember().getRoles().toString().equals(Collections.singletonList(ROLE_MEMBER).toString())) throw new BoardNotHavePermissionToDelete();

        boardRepository.deleteById(id);
        return board.getId();
    }

}
