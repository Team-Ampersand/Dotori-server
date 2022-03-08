package com.server.Dotori.model.board.service;

import com.server.Dotori.model.board.Board;
import com.server.Dotori.model.board.dto.BoardDto;
import com.server.Dotori.model.board.dto.BoardGetDto;
import com.server.Dotori.model.board.dto.BoardGetIdDto;
import com.server.Dotori.model.board.repository.BoardRepository;
import com.server.Dotori.model.member.Member;
import com.server.Dotori.new_exception.DotoriException;
import com.server.Dotori.util.CurrentMemberUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static com.server.Dotori.new_exception.ErrorCode.BOARD_EMPTY;
import static com.server.Dotori.new_exception.ErrorCode.BOARD_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final CurrentMemberUtil currentMemberUtil;
    private final S3Service s3Service;

    /**
     * 공지사항을 생성하는 서비스로직 (기자위, 사감쌤, 개발자만 가능)
     * @param boardDto boardDto
     * @return board
     * @author 배태현
     */
    @Override
    public Board createBoard(BoardDto boardDto, MultipartFile multipartFileList) {
        Member currentMember = currentMemberUtil.getCurrentMember();
        String uploadFile = null;
        try {
            uploadFile = s3Service.uploadFile(multipartFileList);
            return boardRepository.save(boardDto.saveToEntity(currentMember, uploadFile));
        } catch (NullPointerException e) {
            return boardRepository.save(boardDto.saveToEntity(currentMember, uploadFile));
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
        Page<Board> boardPage = boardRepository.findAll(pageable);

        if (boardPage.isEmpty()) throw new DotoriException(BOARD_EMPTY);

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
     * @exception DotoriException (BOARD_NOT_FOUND) 해당 Id의 공지사항을 찾을 수 없을 때
     * @return BoardGetDto (id, title, content,  roles, createdDate, modifiedDate)
     * @author 배태현
     */
    @Override
    public BoardGetIdDto getBoardById(Long boardId) {

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new DotoriException(BOARD_NOT_FOUND));

        ModelMapper modelMapper = new ModelMapper();
        BoardGetIdDto map = modelMapper.map(board, BoardGetIdDto.class);
        map.setRoles(board.getMember().getRoles());

        return map;
    }

    /**
     * 공지사항 수정 서비스 로직 (기자위, 사감쌤, 개발자 권한 사용가능)
     * @param boardId boardId
     * @param boardUpdateDto boardUpdateDto (title, content)
     * @exception DotoriException (BOARD_NOT_FOUND) 해당 Id의 공지사항을 찾을 수 없을 때
     * @return Board
     * @author 배태현
     */
    @Override
    @Transactional
    public Board updateBoard(Long boardId, BoardDto boardUpdateDto) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new DotoriException(BOARD_NOT_FOUND));

        board.updateBoard(boardUpdateDto.getTitle(), boardUpdateDto.getContent());

        return board;
    }

    /**
     * 공지사항 삭제 서비스 로직 (기자위, 사감쌤, 개발자 권한 사용가능)
     * @param boardId boardId
     * @exception DotoriException (BOARD_NOT_FOUND) 해당 Id의 공지사항을 찾을 수 없을 때
     * @author 배태현
     */
    @Override
    public void deleteBoard(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new DotoriException(BOARD_NOT_FOUND));

        try {
            s3Service.deleteFile(board.getUrl().substring(54));
            boardRepository.deleteById(board.getId());
        } catch (NullPointerException e) {
            boardRepository.deleteById(board.getId());
        }
    }
}
