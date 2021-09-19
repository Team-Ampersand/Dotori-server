package com.server.Dotori.model.board.service;

import com.querydsl.core.Tuple;
import com.server.Dotori.exception.board.exception.BoardEmptyException;
import com.server.Dotori.exception.board.exception.BoardNotFoundException;
import com.server.Dotori.model.board.Board;
import com.server.Dotori.model.board.dto.BoardGetDto;
import com.server.Dotori.model.board.dto.BoardDto;
import com.server.Dotori.model.board.dto.BoardGetIdDto;
import com.server.Dotori.model.board.repository.BoardRepository;
import com.server.Dotori.model.member.Member;
import com.server.Dotori.util.CurrentUserUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final CurrentUserUtil currentUserUtil;

    /**
     * 공지사항을 생성하는 서비스로직 (기자위, 사감쌤, 개발자만 가능)
     * @param boardDto boardDto
     * @return board
     * @author 배태현
     */
    @Override
    public Board createBoard(BoardDto boardDto) {
        Member currentUser = currentUserUtil.getCurrentUser();

        return boardRepository.save(boardDto.saveToEntity(currentUser));
    }

    /**
     * 공지사항 전체조회 서비스 로직 (로그인 되어있는 유저 사용가능)
     * @param pageable pageble(7)
     * @return Page-BoardGetDto (id, title, roles, createdDate)
     * @author 배태현
     */
    @Override
    public Page<BoardGetDto> getAllBoard(Pageable pageable) {
        Page<Board> boardPage = boardRepository.findAll(pageable);

        if (boardPage.isEmpty()) throw new BoardEmptyException();

        return boardPage.map(board -> {
            ModelMapper mapper = new ModelMapper();
            BoardGetDto map = mapper.map(board, BoardGetDto.class);
            map.setRoles(board.getMember().getRoles());

            return map;
        });
    }

    /**
     * 공지사항 상세조회 서비스 로직 (로그인 되어있을 시 사용가능)
     * @param boardId boardId
     * @return BoardGetDto (id, title, content,  roles, createdDate, modifiedDate)
     * @author 배태현
     */
    @Override
    public BoardGetIdDto getBoardById(Long boardId) {

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException());

        ModelMapper modelMapper = new ModelMapper();
        BoardGetIdDto map = modelMapper.map(board, BoardGetIdDto.class);
        map.setRoles(board.getMember().getRoles());

        return map;
    }

    /**
     * 공지사항 수정 서비스 로직 (기자위, 사감쌤, 개발자 권한 사용가능)
     * @param boardId boardId
     * @param boardUpdateDto boardUpdateDto (title, content)
     * @return Board
     * @author 배태현
     */
    @Override
    @Transactional
    public Board updateBoard(Long boardId, BoardDto boardUpdateDto) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException());

        board.updateBoard(boardUpdateDto.getTitle(), boardUpdateDto.getContent());

        return board;
    }

    /**
     * 공지사항 삭제 서비스 로직 (기자위, 사감쌤, 개발자 권한 사용가능)
     * @param boardId boardId
     * @author 배태현
     */
    @Override
    public void deleteBoard(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException());

        boardRepository.deleteById(board.getId());
    }
}
