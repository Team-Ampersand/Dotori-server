package com.server.Dotori.model.music.dto;

import com.server.Dotori.model.board.Board;
import com.server.Dotori.model.member.Member;
import com.server.Dotori.model.music.Music;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter @Builder
@NoArgsConstructor @AllArgsConstructor
public class MusicApplicationDto {

    @NotNull
    private String musicUrl;

    public Music saveToEntity(Member member) {
        return Music.builder()
                .member(member)
                .url(musicUrl)
                .build();
    }
}