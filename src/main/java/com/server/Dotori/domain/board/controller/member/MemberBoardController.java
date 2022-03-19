package com.server.Dotori.domain.board.controller.member;

import com.server.Dotori.domain.board.dto.BoardGetDto;
import com.server.Dotori.domain.board.dto.BoardGetIdDto;
import com.server.Dotori.domain.board.service.BoardService;
import com.server.Dotori.global.response.ResponseService;
import com.server.Dotori.global.response.result.SingleResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/member")
@RequiredArgsConstructor
public class MemberBoardController {

    private final BoardService boardService;
    private final ResponseService responseService;

    /**
     * 공지사항 전체 조회 컨트롤러
     * @param pageable 7
     * @return SingleResult (Page-BoardGetDto)
     * @author 배태현
     */
    @GetMapping("/board")
    @ResponseStatus( HttpStatus.OK )
    @ApiOperation(value = "공지사항 전체 조회", notes = "공지사항 전체 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    public SingleResult<Page<BoardGetDto>> getAllBoardMember(@PageableDefault(size = 7) Pageable pageable) {
        Page<BoardGetDto> pageBoard = boardService.getAllBoard(pageable);
        return responseService.getSingleResult(pageBoard);
    }

    /**
     * 공지사항 상세조회 컨트롤러
     * @param boardId boardId
     * @return SingleResult (BoardGetIdDto)
     * @author 배태현
     */
    @GetMapping("/board/{id}")
    @ResponseStatus( HttpStatus.OK )
    @ApiOperation(value = "공지사항 상세 조회", notes = "공지사항 상세 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    public SingleResult<BoardGetIdDto> getBoardByIdMember(@PathVariable("id") Long boardId) {
        BoardGetIdDto findBoardById = boardService.getBoardById(boardId);
        return responseService.getSingleResult(findBoardById);
    }
}
