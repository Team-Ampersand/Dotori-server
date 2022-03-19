package com.server.Dotori.model.board.controller.common;

import com.server.Dotori.model.board.service.BoardService;
import com.server.Dotori.response.ResponseService;
import com.server.Dotori.response.result.SingleResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/board")
public class CommonBoardController {

    private final BoardService boardService;
    private final ResponseService responseService;

    @GetMapping(value = "/count")
    @ResponseStatus( HttpStatus.OK )
    public SingleResult countBoard() {
        return responseService.getSingleResult(boardService.countBoard());
    }
}
