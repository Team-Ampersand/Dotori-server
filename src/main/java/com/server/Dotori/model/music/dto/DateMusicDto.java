package com.server.Dotori.model.music.dto;

import lombok.*;

import java.time.LocalDate;

@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED) @AllArgsConstructor
public class DateMusicDto {

    private LocalDate date;
}
