package com.server.Dotori.model.board.service;

import com.server.Dotori.model.board.Board;
import com.server.Dotori.model.board.dto.BoardSaveDto;
import com.server.Dotori.model.board.repository.BoardRepository;
import com.server.Dotori.model.member.Member;
import com.server.Dotori.util.CurrentUserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
