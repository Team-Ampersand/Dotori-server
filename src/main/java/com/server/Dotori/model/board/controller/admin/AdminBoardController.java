package com.server.Dotori.model.board.controller.admin;

import com.server.Dotori.model.board.dto.BoardAllResponseDto;
import com.server.Dotori.model.board.dto.BoardDto;
import com.server.Dotori.model.board.dto.BoardResponseDto;
import com.server.Dotori.model.board.service.BoardService;
import com.server.Dotori.response.ResponseService;
import com.server.Dotori.response.result.CommonResult;
import com.server.Dotori.response.result.SingleResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/v1/admin")
@RequiredArgsConstructor
public class AdminBoardController {

    private final BoardService boardService;
    private final ResponseService responseService;

    @PostMapping("/board")
    public CommonResult createBoardByAdmin(@RequestBody BoardDto boardDto, HttpServletRequest request) {
        boardService.createBoard(boardDto, request);
        return responseService.getSuccessResult();
    }

    @GetMapping("/board/{id}")
    public CommonResult readBoardById_Admin(@PathVariable("id") Long id) {
        BoardResponseDto board = boardService.readBoardById(id);
        return responseService.getSingleResult(board);
    }

    @GetMapping("/board")
    public SingleResult<Page<BoardAllResponseDto>> readAllBoard_Admin(@PageableDefault(size = 5) Pageable pageable) {
        Page<BoardAllResponseDto> boards = boardService.readAllBoard(pageable);
        return responseService.getSingleResult(boards);
    }

    @PutMapping("/board/{id}")
    public CommonResult updateBoard_Admin(@PathVariable("id") Long id, BoardDto boardDto, HttpServletRequest request) {
        boardService.updateBoard(id, boardDto, request);
        return responseService.getSuccessResult();
    }

    @DeleteMapping("/board/{id}")
    public CommonResult deleteBoard(@PathVariable("id") Long id, HttpServletRequest request) {
        boardService.deleteBoard(id, request);
        return responseService.getSuccessResult();
    }
}
