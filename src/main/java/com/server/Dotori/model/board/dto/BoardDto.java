package com.server.Dotori.model.board.dto;

import com.server.Dotori.model.board.Board;
import com.server.Dotori.model.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    public Board saveToEntity(Member member) {
        return Board.builder()
                .title(title)
                .content(content)
                .member(member)
                .build();
    }
}
