package com.server.Dotori.domain.main_page.service.Impl;

import com.server.Dotori.domain.board.Board;
import com.server.Dotori.domain.board.repository.BoardRepository;
import com.server.Dotori.domain.main_page.dto.BoardAlarmDto;
import com.server.Dotori.domain.main_page.service.BoardAlarmService;
import com.server.Dotori.global.exception.DotoriException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.server.Dotori.global.exception.ErrorCode.BOARD_EMPTY;

@Service
@RequiredArgsConstructor
public class BoardAlarmServiceImpl implements BoardAlarmService {

    private final BoardRepository boardRepository;

    /**
     * 새로운 공지사항 등록 여부를 판단할 수 있는 서비스 로직 (로그인 된 유저 사용가능)
     * @return board count
     * @author 배태현
     */
    @Override
    public BoardAlarmDto getBoardAlarmInfo() {
        Board lastWriteBoard = boardRepository.findTop1ByOrderByCreatedDateDesc();

        if (lastWriteBoard == null) throw new DotoriException(BOARD_EMPTY);

        return BoardAlarmDto.builder()
                .id(lastWriteBoard.getId())
                .title(lastWriteBoard.getTitle())
                .writerRole(lastWriteBoard.getMember().getRoles())
                .lastBoardWriteDate(lastWriteBoard.getCreatedDate().toLocalDate())
                .build();
    }
}
