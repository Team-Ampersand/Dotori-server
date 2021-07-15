package com.server.Dotori.model.board.controller;

import com.server.Dotori.model.board.Board;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("v1")
public class BoardController {

    private final BoardService boardService;
    private final ResponseService responseService;

    /*** create Board ***/
    @PostMapping("/admin/board")
    public CommonResult createBoardByAdmin(@RequestBody BoardDto boardDto) {
        boardService.createBoard(boardDto);
        return responseService.getSuccessResult();
    }

    @PostMapping("/councillor/board")
    public CommonResult createBoardByCouncillor(@RequestBody BoardDto boardDto) {
        boardService.createBoard(boardDto);
        return responseService.getSuccessResult();
    }

    /*** read Board Id ***/
    @GetMapping("/member/board/{id}")
    public CommonResult readBoardById(@PathVariable("id") Long id) {
        BoardResponseDto board = boardService.readBoardById(id);
        return responseService.getSingleResult(board);
    }

    @GetMapping("/admin/board/{id}")
    public CommonResult readBoardById_Admin(@PathVariable("id") Long id) {
        BoardResponseDto board = boardService.readBoardById(id);
        return responseService.getSingleResult(board);
    }

    @GetMapping("/councillor/board/{id}")
    public CommonResult readBoardById_Councillor(@PathVariable("id") Long id) {
        BoardResponseDto board = boardService.readBoardById(id);
        return responseService.getSingleResult(board);
    }

    /*** read All Board ***/
    @GetMapping("/member/board")
    public SingleResult<Page<BoardAllResponseDto>> readAllBoard(@PageableDefault(size = 5) Pageable pageable) {
        Page<BoardAllResponseDto> boards = boardService.readAllBoard(pageable);
        return responseService.getSingleResult(boards);
    }

    @GetMapping("/admin/board")
    public SingleResult<Page<BoardAllResponseDto>> readAllBoard_Admin(@PageableDefault(size = 5) Pageable pageable) {
        Page<BoardAllResponseDto> boards = boardService.readAllBoard(pageable);
        return responseService.getSingleResult(boards);
    }

    @GetMapping("/councillor/board")
    public SingleResult<Page<BoardAllResponseDto>> readAllBoard_Councillor(@PageableDefault(size = 5) Pageable pageable) {
        Page<BoardAllResponseDto> boards = boardService.readAllBoard(pageable);
        return responseService.getSingleResult(boards);
    }

    /*** update board ***/


    /*** delete board ***/
    @DeleteMapping("/admin/board/{id}")
    public CommonResult deleteBoard(@PathVariable("id") Long id) {
        boardService.deleteBoard(id);
        return responseService.getSuccessResult();
    }

    @DeleteMapping("/councillor/board/{id}")
    public CommonResult deleteBoard_Councillor(@PathVariable("id") Long id) {
        boardService.deleteBoard(id);
        return responseService.getSuccessResult();
    }
}
