package com.server.Dotori.model.board.controller.developer;

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
@RequestMapping("/v1/developer")
@RequiredArgsConstructor
public class DeveloperController {

    private final BoardService boardService;
    private final ResponseService responseService;

    @PostMapping("/board")
    public CommonResult createBoardByDeveloper(@RequestBody BoardDto boardDto) {
        boardService.createBoard(boardDto);
        return responseService.getSuccessResult();
    }

    @GetMapping("/board/{id}")
    public CommonResult readBoardById_Developer(@PathVariable("id") Long id) {
        BoardResponseDto board = boardService.readBoardById(id);
        return responseService.getSingleResult(board);
    }

    @GetMapping("/board")
    public SingleResult<Page<BoardAllResponseDto>> readAllBoard_Developer(@PageableDefault(size = 5) Pageable pageable) {
        Page<BoardAllResponseDto> boards = boardService.readAllBoard(pageable);
        return responseService.getSingleResult(boards);
    }

    @DeleteMapping("/board/{id}")
    public CommonResult deleteBoard_Developer(@PathVariable("id") Long id) {
        boardService.deleteBoard(id);
        return responseService.getSuccessResult();
    }
}
