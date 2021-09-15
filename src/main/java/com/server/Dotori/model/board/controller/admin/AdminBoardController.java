package com.server.Dotori.model.board.controller.admin;

import com.server.Dotori.model.board.dto.BoardGetDto;
import com.server.Dotori.model.board.dto.BoardDto;
import com.server.Dotori.model.board.dto.BoardGetIdDto;
import com.server.Dotori.model.board.service.BoardService;
import com.server.Dotori.response.ResponseService;
import com.server.Dotori.response.result.CommonResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/admin")
@RequiredArgsConstructor
public class AdminBoardController {

    private final BoardService boardService;
    private final ResponseService responseService;

    @PostMapping("/board")
    @ResponseStatus( HttpStatus.CREATED )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    public CommonResult createBoard(@RequestBody BoardDto boardDto) {
        boardService.createBoard(boardDto);
        return responseService.getSuccessResult();
    }

    @GetMapping("/board")
    @ResponseStatus( HttpStatus.OK )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    public CommonResult getAllBoard(@PageableDefault(size = 5) Pageable pageable) {
        Page<BoardGetDto> pageBoard = boardService.getAllBoard(pageable);
        return responseService.getSingleResult(pageBoard);
    }

    @GetMapping("/board/{id}")
    @ResponseStatus( HttpStatus.OK )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    public CommonResult getBoardById(@PathVariable("id") Long boardId) {
        BoardGetIdDto findBoardById = boardService.getBoardById(boardId);
        return responseService.getSingleResult(findBoardById);
    }

    @PutMapping("/board/{id}")
    @ResponseStatus( HttpStatus.OK )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    public CommonResult updateBoard(@PathVariable("id") Long boardId, @RequestBody BoardDto boardUpdateDto) {
        boardService.updateBoard(boardId, boardUpdateDto);
        return responseService.getSuccessResult();
    }

    @DeleteMapping("/board/{id}")
    @ResponseStatus( HttpStatus.NO_CONTENT )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    public CommonResult deleteBoard(@PathVariable("id") Long boardId) {
        boardService.deleteBoard(boardId);
        return responseService.getSuccessResult();
    }
}
