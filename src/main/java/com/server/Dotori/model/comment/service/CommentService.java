package com.server.Dotori.model.comment.service;

import com.server.Dotori.model.comment.Comment;
import com.server.Dotori.model.comment.dto.CommentDto;

public interface CommentService {

    Comment createComment(Long boardId, CommentDto commentDto);
}