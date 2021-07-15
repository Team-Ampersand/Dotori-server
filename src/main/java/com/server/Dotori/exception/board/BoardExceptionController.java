package com.server.Dotori.exception.board;

import com.server.Dotori.exception.board.exception.BoardNotFoundException;
import com.server.Dotori.response.result.CommonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exception")
public class BoardExceptionController {

    @GetMapping("/board-not-found")
    public CommonResult boardNotFoundException() {throw new BoardNotFoundException();}
}
