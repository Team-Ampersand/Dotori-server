package com.server.Dotori.model.board.controller.admin;

import com.server.Dotori.model.board.dto.BoardGetDto;
import com.server.Dotori.model.board.dto.BoardSaveDto;
import com.server.Dotori.model.board.service.BoardService;
import com.server.Dotori.response.ResponseService;
import com.server.Dotori.response.result.CommonResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/admin")
@RequiredArgsConstructor
public class AdminBoardController {

    private final BoardService boardService;
    private final ResponseService responseService;

    @PostMapping("/board")
    public CommonResult createBoard(@RequestBody BoardSaveDto boardSaveDto) {
        boardService.createBoard(boardSaveDto);
        return responseService.getSuccessResult();
    }

    @GetMapping("/board")
    public CommonResult getAllBoard(@PageableDefault(size = 5) Pageable pageable) {
        Page<BoardGetDto> pageBoard = boardService.getAllBoard(pageable);
        return responseService.getSingleResult(pageBoard);
    }
}
