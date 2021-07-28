package com.server.Dotori.model.board.service;

import com.server.Dotori.exception.board.exception.BoardNotFoundException;
import com.server.Dotori.model.board.Board;
import com.server.Dotori.model.board.dto.BoardGetDto;
import com.server.Dotori.model.board.dto.BoardGetIdDto;
import com.server.Dotori.model.board.dto.BoardSaveDto;
import com.server.Dotori.model.board.repository.BoardRepository;
import com.server.Dotori.model.member.Member;
import com.server.Dotori.model.member.enumType.Role;
import com.server.Dotori.util.CurrentUserUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final CurrentUserUtil currentUserUtil;

    @Override
    public Board createBoard(BoardSaveDto boardSaveDto) {
        Member currentUser = currentUserUtil.getCurrentUser();
        return boardRepository.save(boardSaveDto.saveToEntity(currentUser));
    }

    @Override
    public Page<BoardGetDto> getAllBoard(Pageable pageable) {
        Page<Board> boardPage = boardRepository.findAll(pageable);

        return boardPage.map(board -> {
            ModelMapper mapper = new ModelMapper();
            BoardGetDto map = mapper.map(board, BoardGetDto.class);
            map.setRoles(board.getMember().getRoles());

            return map;
        });
    }

    @Override
    public BoardGetIdDto getBoardById(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException());

        ModelMapper modelMapper = new ModelMapper();
        BoardGetIdDto map = modelMapper.map(board, BoardGetIdDto.class);
        map.setRoles(board.getMember().getRoles());

        return map;
    }
}
