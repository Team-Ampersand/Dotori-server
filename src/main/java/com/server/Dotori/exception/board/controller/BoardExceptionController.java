package com.server.Dotori.exception.board.controller;

import com.server.Dotori.exception.board.exception.*;
import com.server.Dotori.response.result.CommonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exception")
public class BoardExceptionController {

    @GetMapping("/board-not-search")
    public CommonResult boardNotSearchException() {throw new BoardNotSearchException();}

    @GetMapping("/board-not-have-permission-to-create")
    public CommonResult boardNotHavePermissionToCreate() {throw new BoardNotHavePermissionToCreate();}

    @GetMapping("/board-not-have-permission-to-modify")
    public CommonResult boardNotHavePermissionToModify() {throw new BoardNotHavePermissionToModify();}

    @GetMapping("/board-not-have-permission-to-delete")
    public CommonResult boardNotHavePermissionToDelete() {throw new BoardNotHavePermissionToDelete();}

    @GetMapping("/board-empty")
    public CommonResult boardEmptyException() {throw new BoardEmptyException();}
}
