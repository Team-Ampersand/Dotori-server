package com.server.Dotori.exception.board;

import com.server.Dotori.exception.board.exception.BoardNotFoundException;
import com.server.Dotori.exception.board.exception.BoardNotHavePermissionToCreate;
import com.server.Dotori.exception.board.exception.BoardNotHavePermissionToDelete;
import com.server.Dotori.exception.board.exception.BoardNotHavePermissionToModify;
import com.server.Dotori.response.result.CommonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exception")
public class BoardExceptionController {

    @GetMapping("/board-not-found")
    public CommonResult boardNotFoundException() {throw new BoardNotFoundException();}

    @GetMapping("/board-not-have-permission-to-create")
    public CommonResult boardNotHavePermissionToCreate() {throw new BoardNotHavePermissionToCreate();}

    @GetMapping("/board-not-have-permission-to-modify")
    public CommonResult boardNotHavePermissionToModify() {throw new BoardNotHavePermissionToModify();}

    @GetMapping("/board-not-have-permission-to-delete")
    public CommonResult boardNotHavePermissionToDelete() {throw new BoardNotHavePermissionToDelete();}
}
