package com.server.Dotori.model.board.controller.councillor;

import com.server.Dotori.model.board.dto.BoardDto;
import com.server.Dotori.model.board.dto.BoardGetDto;
import com.server.Dotori.model.board.dto.BoardGetIdDto;
import com.server.Dotori.model.board.service.BoardService;
import com.server.Dotori.response.ResponseService;
import com.server.Dotori.response.result.CommonResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/councillor")
@RequiredArgsConstructor
public class CouncillorBoardController {

    private final BoardService boardService;
    private final ResponseService responseService;

    /**
     * 공지사항 생성 컨트롤러
     * @param boardDto (title, content)
     * @return CommonResult - SuccessResult
     * @author 배태현
     */
    @PostMapping("/board")
    @ResponseStatus( HttpStatus.CREATED )
    @ApiOperation(value = "공지사항 작성", notes = "공지사항 작성")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    public CommonResult createBoard(@RequestBody BoardDto boardDto) {
        boardService.createBoard(boardDto);
        return responseService.getSuccessResult();
    }

    /**
     * 공지사항 전체 조회 컨트롤러
     * @param pageable 7
     * @return CommonResult - SingleResult (Page-BoardGetDto)
     * @author 배태현
     */
    @GetMapping("/board")
    @ResponseStatus( HttpStatus.OK )
    @ApiOperation(value = "공지사항 전체 조회", notes = "공지사항 전체 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    public CommonResult getAllBoard(@PageableDefault(size = 7) Pageable pageable) {
        Page<BoardGetDto> pageBoard = boardService.getAllBoard(pageable);
        return responseService.getSingleResult(pageBoard);
    }

    /**
     * 공지사항 상세조회 컨트롤러
     * @param boardId boardId
     * @return CommonResult - SingleResult (BoardGetIdDto)
     * @author 배태현
     */
    @GetMapping("/board/{id}")
    @ResponseStatus( HttpStatus.OK )
    @ApiOperation(value = "공지사항 상세 조회", notes = "공지사항 상세 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    public CommonResult getBoardById(@PathVariable("id") Long boardId) {
        BoardGetIdDto findBoardById = boardService.getBoardById(boardId);
        return responseService.getSingleResult(findBoardById);
    }

    /**
     * 공지사항 수정 컨트롤러
     * @param boardId boardId
     * @param boardUpdateDto (title, content)
     * @return CommonResult - SuccessResult
     * @author 배태현
     */
    @PutMapping("/board/{id}")
    @ResponseStatus( HttpStatus.OK )
    @ApiOperation(value = "공지사항 수정", notes = "공지사항 수정")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    public CommonResult updateBoard(@PathVariable("id") Long boardId, @RequestBody BoardDto boardUpdateDto) {
        boardService.updateBoard(boardId, boardUpdateDto);
        return responseService.getSuccessResult();
    }

    /**
     * 공지사항 삭제 컨트롤러
     * @param boardId boardId
     * @return CommonResult - SuccessResult
     * @author 배태현
     */
    @DeleteMapping("/board/{id}")
    @ResponseStatus( HttpStatus.NO_CONTENT )
    @ApiOperation(value = "공지사항 삭제", notes = "공지사항 삭제")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    public CommonResult deleteBoard(@PathVariable("id") Long boardId) {
        boardService.deleteBoard(boardId);
        return responseService.getSuccessResult();
    }
}
