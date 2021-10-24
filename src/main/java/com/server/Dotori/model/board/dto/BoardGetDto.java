package com.server.Dotori.model.board.dto;

import com.server.Dotori.model.member.enumType.Role;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED) @AllArgsConstructor
public class BoardGetDto {

    private Long id;
    private String title;
    private List<Role> roles;

    @CreatedDate
    private LocalDateTime createdDate;
}
