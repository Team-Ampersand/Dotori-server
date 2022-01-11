package com.server.Dotori.model.board.dto;

import com.server.Dotori.model.member.enumType.Role;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED) @AllArgsConstructor
public class BoardGetIdDto {

    private Long id;
    private String title;
    private String content;
    private List<Role> roles;
    private String url;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

}
