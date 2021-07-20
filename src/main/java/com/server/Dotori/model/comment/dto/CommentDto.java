package com.server.Dotori.model.comment.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.server.Dotori.model.board.Board;
import com.server.Dotori.model.comment.Comment;
import com.server.Dotori.model.member.Member;
import com.server.Dotori.model.member.enumType.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class CommentDto {

    private String contents;

    @JsonIgnore
    private Board board;
    @JsonIgnore
    private Member member;

    public Comment toEntity(String username) {
        return Comment.builder()
                .contents(contents)
                .writer(username)
                .board(board)
                .member(member)
                .build();
    }
}
