package com.server.Dotori.domain.board.dto;

import com.server.Dotori.domain.member.enumType.Role;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Getter @Setter @Builder
@NoArgsConstructor(access = PROTECTED) @AllArgsConstructor
public class BoardGetDto {

    private Long id;
    private String title;
    private List<Role> roles;

    private LocalDateTime createdDate;
}
