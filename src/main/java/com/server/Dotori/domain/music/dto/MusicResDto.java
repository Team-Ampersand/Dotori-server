package com.server.Dotori.domain.music.dto;

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
    private String memberName;
    private String email;

    @CreatedDate
    private LocalDateTime createdDate;
}
