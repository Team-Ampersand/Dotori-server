package com.server.Dotori.domain.main_page.dto;

import com.server.Dotori.domain.member.enumType.Role;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED) @AllArgsConstructor
public class BoardAlarmDto {

    private Long id;
    private String title;
    private List<Role> writerRole;
    private LocalDate lastBoardWriteDate;
}
