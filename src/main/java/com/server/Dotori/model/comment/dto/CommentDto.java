package com.server.Dotori.model.comment.dto;

import com.server.Dotori.model.board.Board;
import com.server.Dotori.model.comment.Comment;
import com.server.Dotori.model.member.Member;
import lombok.*;

@Getter @Setter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private String contents;

    public Comment saveToEntity(Board board, Member member) {
        return Comment.builder()
                .board(board)
                .member(member)
                .contents(contents)
                .build();
    }
}