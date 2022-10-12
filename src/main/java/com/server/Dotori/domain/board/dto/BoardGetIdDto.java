package com.server.Dotori.domain.board.dto;

import com.server.Dotori.domain.board.BoardImage;
import com.server.Dotori.domain.member.enumType.Role;
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
    private List<BoardImage> boardImages;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

}
