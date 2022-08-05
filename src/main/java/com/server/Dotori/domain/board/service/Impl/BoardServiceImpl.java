package com.server.Dotori.domain.board.service.Impl;

import com.server.Dotori.domain.board.Board;
import com.server.Dotori.domain.board.BoardImage;
import com.server.Dotori.domain.board.dto.BoardDto;
import com.server.Dotori.domain.board.dto.BoardGetDto;
import com.server.Dotori.domain.board.dto.BoardGetIdDto;
import com.server.Dotori.domain.board.repository.BoardImageRepository;
import com.server.Dotori.domain.board.repository.BoardRepository;
import com.server.Dotori.domain.board.service.BoardService;
import com.server.Dotori.domain.board.service.S3Service;
import com.server.Dotori.domain.member.Member;
import com.server.Dotori.global.exception.DotoriException;
import com.server.Dotori.global.exception.ErrorCode;
import com.server.Dotori.global.util.CurrentMemberUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final CurrentMemberUtil currentMemberUtil;
    private final S3Service s3Service;
    private final BoardImageRepository boardImageRepository;

    /**
     * 공지사항을 생성하는 서비스로직 (기자위, 사감쌤, 개발자만 가능)
     * @param boardDto boardDto
     * @return board
     * @author 배태현
     */
    @Override
    public Board createBoard(BoardDto boardDto, List<MultipartFile> multipartFileList) {
        Member currentMember = currentMemberUtil.getCurrentMember();
        List<String> uploadFile = null;
        Board board = null;
        try {
            uploadFile = s3Service.uploadFile(multipartFileList);
            board = boardRepository.save(boardDto.saveToEntity(currentMember));
            for (String uploadFileUrl : uploadFile) {
                boardImageRepository.save(new BoardImage(null,board,uploadFileUrl));
            }

            return board;
        } catch (NullPointerException e) {
            return boardRepository.save(boardDto.saveToEntity(currentMember));
        }
    }

    /**
     * 공지사항 전체조회 서비스 로직 (로그인 되어있는 유저 사용가능)
     * @param pageable pageble(7)
     * @exception DotoriException (BOARD_EMPTY) 작성된 공지사항이 없을 때
     * @return Page-BoardGetDto (id, title, roles, createdDate)
     * @author 배태현
     */
    @Override
    public Page<BoardGetDto> getAllBoard(Pageable pageable) {
        Page<Board> boardPage = boardRepository.getAllBoardCreateDateDesc(pageable);

        if (boardPage.isEmpty()) throw new DotoriException(ErrorCode.BOARD_EMPTY);

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
        Board board = getBoard(boardId);

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
        Board board = getBoard(boardId);

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
        Board board = getBoard(boardId);
        String url = boardImageRepository.findBoardImageByBoardId(boardId);
        try {
            s3Service.deleteFile(url.substring(54));
            boardRepository.deleteById(board.getId());
        } catch (NullPointerException e) {
            boardRepository.deleteById(board.getId());
        }
    }

    /**
     * board가 존재하는지 check 해준 뒤 존재하다면 Board를 반환해주는 메서드
     * @param boardId
     * @exception DotoriException (BOARD_NOT_FOUND) 해당 Id의 공지사항을 찾을 수 없을 때
     * @return Board Entity
     * @author 배태현
     */
    private Board getBoard(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new DotoriException(ErrorCode.BOARD_NOT_FOUND));
    }
}
