package com.server.Dotori.model.music.dto;

import com.server.Dotori.model.member.Member;
import com.server.Dotori.model.music.Music;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED) @AllArgsConstructor
public class MusicApplicationDto {

    @NotBlank
    private String musicUrl;

    public Music saveToEntity(Member member) {
        return Music.builder()
                .member(member)
                .url(musicUrl)
                .build();
    }
}
