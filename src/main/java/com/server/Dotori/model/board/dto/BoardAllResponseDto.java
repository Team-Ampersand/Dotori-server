package com.server.Dotori.model.board.dto;

import com.server.Dotori.model.comment.Comment;
import com.server.Dotori.model.member.enumType.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BoardAllResponseDto {

    private Long id;
    private String title;
    private List<Role> roles;

    @CreatedDate
    private LocalDateTime createdDate;
}
