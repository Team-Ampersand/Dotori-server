package com.server.Dotori.model.comment.service;

import com.server.Dotori.model.comment.dto.CommentDto;

public interface CommentService {

    Long createComment(Long boardId, CommentDto commentDto);
}