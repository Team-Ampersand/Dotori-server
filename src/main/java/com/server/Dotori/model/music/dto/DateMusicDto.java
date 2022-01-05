package com.server.Dotori.model.music.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED) @AllArgsConstructor
public class DateMusicDto {

    @NotBlank
    private LocalDate date;
}
