package com.server.Dotori.domain.board.dto;

import com.server.Dotori.domain.member.enumType.Role;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED) @AllArgsConstructor
public class BoardGetDto {

    private Long id;
    private String title;
    private List<Role> roles;

    private LocalDateTime createdDate;
}
