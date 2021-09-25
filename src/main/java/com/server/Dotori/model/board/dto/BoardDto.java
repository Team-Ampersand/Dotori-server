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
    @Size(min = 1, max = 45)
    private String title;

    @NotBlank
    @Size(min = 1, max = 500)
    private String content;

    public Board saveToEntity(Member member) {
        return Board.builder()
                .title(title)
                .content(content)
                .member(member)
                .build();
    }
}
