package com.server.Dotori.domain.music.dto;

import com.server.Dotori.domain.member.Member;
import com.server.Dotori.domain.music.Music;
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
