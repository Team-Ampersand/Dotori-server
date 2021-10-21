package com.server.Dotori.model.board.dto;

import com.server.Dotori.model.board.Board;
import com.server.Dotori.model.member.Member;
import lombok.*;

import javax.validation.constraints.*;

@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
