package com.server.Dotori.model.comment.service;

import com.server.Dotori.model.comment.dto.CommentDto;

import javax.servlet.http.HttpServletRequest;

public interface CommentService {

    Long saveComment(Long id, CommentDto commentDto, HttpServletRequest request);
}
