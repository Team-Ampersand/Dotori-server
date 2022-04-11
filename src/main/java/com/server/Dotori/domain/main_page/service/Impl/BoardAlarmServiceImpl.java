package com.server.Dotori.domain.main_page.service.Impl;

import com.server.Dotori.domain.board.Board;
import com.server.Dotori.domain.main_page.dto.BoardAlarmDto;
import com.server.Dotori.domain.board.repository.BoardRepository;
import com.server.Dotori.domain.main_page.service.BoardAlarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

        return BoardAlarmDto.builder()
                .id(lastWriteBoard.getId())
                .title(lastWriteBoard.getTitle())
                .writerRole(lastWriteBoard.getMember().getRoles())
                .lastBoardWriteDate(lastWriteBoard.getCreatedDate().toLocalDate())
                .build();
    }
}
