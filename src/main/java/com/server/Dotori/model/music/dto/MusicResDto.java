package com.server.Dotori.model.music.dto;

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

    @CreatedDate
    private LocalDateTime createdDate;
}
