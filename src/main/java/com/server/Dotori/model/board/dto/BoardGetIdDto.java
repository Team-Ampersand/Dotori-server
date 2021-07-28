package com.server.Dotori.model.board.dto;

import com.server.Dotori.model.comment.Comment;
import com.server.Dotori.model.member.enumType.Role;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardGetIdDto {

    private Long id;
    private String title;
    private String content;
    private List<Comment> comments;
    private List<Role> roles;

    @CreatedDate
    private LocalDateTime createdDate;
}
