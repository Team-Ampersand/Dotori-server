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
    @Size(min = 1, max = 800)
    private String content;

    public Board saveToEntity(Member member, String url) {
        Board board = Board.builder()
                .title(title)
                .content(content)
                .member(member)
                .url("https://dotori-s3.s3.ap-northeast-2.amazonaws.com/img/" + url)
                .build();
        try {
            if (url.isEmpty()) board.setUrl(null);
        } catch (NullPointerException e) {
            board.setUrl(null);
        }
        return board;
    }
}
