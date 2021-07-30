package com.server.Dotori.model.comment.controller.admin;

import com.server.Dotori.model.comment.dto.CommentDto;
import com.server.Dotori.model.comment.service.CommentService;
import com.server.Dotori.response.ResponseService;
import com.server.Dotori.response.result.CommonResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admin")
public class AdminCommentController {

    private final CommentService commentService;
    private final ResponseService responseService;

    @PostMapping("/board/{id}/comment")
    public CommonResult createComment(@PathVariable("id") Long boardId, @RequestBody CommentDto commentDto) {
        commentService.createComment(boardId, commentDto);
        return responseService.getSuccessResult();
    }
}
