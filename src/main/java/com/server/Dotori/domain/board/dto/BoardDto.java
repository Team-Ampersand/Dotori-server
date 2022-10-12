package com.server.Dotori.domain.board.dto;

import com.server.Dotori.domain.board.Board;
import com.server.Dotori.domain.member.Member;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BoardDto {

    @NotBlank
    @Size(min = 1, max = 45)
    private String title;

    @NotBlank
    @Size(min = 1, max = 5000)
    private String content;

    public Board saveToEntity(Member member) {
        Board board = Board.builder()
                .title(title)
                .content(content)
                .member(member)
                .build();
        return board;
    }

}
