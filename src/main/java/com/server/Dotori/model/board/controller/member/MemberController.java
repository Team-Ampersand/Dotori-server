package com.server.Dotori.model.board.controller.member;

import com.server.Dotori.model.board.dto.BoardAllResponseDto;
import com.server.Dotori.model.board.dto.BoardResponseDto;
import com.server.Dotori.model.board.service.BoardService;
import com.server.Dotori.response.ResponseService;
import com.server.Dotori.response.result.CommonResult;
import com.server.Dotori.response.result.SingleResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/member")
@RequiredArgsConstructor
public class MemberController {

    private final BoardService boardService;
    private final ResponseService responseService;

    @GetMapping("/board/{id}")
    public CommonResult readBoardById(@PathVariable("id") Long id) {
        BoardResponseDto board = boardService.readBoardById(id);
        return responseService.getSingleResult(board);
    }

    @GetMapping("/board")
    public SingleResult<Page<BoardAllResponseDto>> readAllBoard(@PageableDefault(size = 5) Pageable pageable) {
        Page<BoardAllResponseDto> boards = boardService.readAllBoard(pageable);
        return responseService.getSingleResult(boards);
    }
}
