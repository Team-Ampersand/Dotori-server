package com.server.Dotori.model.music.dto;

import com.server.Dotori.model.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter @Builder
@NoArgsConstructor @AllArgsConstructor
public class MusicResDto {

    private Long id;
    private String url;
    private String username;

    @CreatedDate
    private LocalDateTime createdDate;
}
