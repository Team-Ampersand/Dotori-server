package com.server.Dotori.domain.main_page.dto;

import com.server.Dotori.domain.member.enumType.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Getter @Builder
@NoArgsConstructor(access = PROTECTED) @AllArgsConstructor
public class BoardAlarmDto {

    private Long id;
    private String title;
    private List<Role> writerRole;
    private LocalDate lastBoardWriteDate;
}
