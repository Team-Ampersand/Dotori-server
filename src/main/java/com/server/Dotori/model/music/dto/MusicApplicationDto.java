package com.server.Dotori.model.music.dto;

import com.server.Dotori.model.member.Member;
import com.server.Dotori.model.music.Music;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED) @AllArgsConstructor
public class MusicApplicationDto {

    @NotBlank
    @Pattern(regexp = "^((?:https?:)?\\/\\/)?((?:www|m)\\.)?((?:youtube\\.com|youtu.be))(\\/(?:[\\w\\-]+\\?v=|embed\\/|v\\/)?)([\\w\\-]+)(\\S+)?$")
    private String musicUrl;

    public Music saveToEntity(Member member) {
        return Music.builder()
                .member(member)
                .url(musicUrl)
                .build();
    }
}
