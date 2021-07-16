package com.server.Dotori.model.board.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.server.Dotori.model.board.Board;
import com.server.Dotori.model.member.Member;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class BoardDto {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @JsonIgnore
    private Member member;

    public Board toEntity() {
        return Board.builder()
                .title(title)
                .content(content)
                .member(member)
                .build();
    }
}