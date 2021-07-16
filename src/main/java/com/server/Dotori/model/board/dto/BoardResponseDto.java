package com.server.Dotori.model.board.dto;

import com.server.Dotori.model.comment.Comment;
import com.server.Dotori.model.member.Member;
import com.server.Dotori.model.member.enumType.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BoardResponseDto {

    private Long id;
    private String title;
    private String content;
    private List<Role> roles;
    private List<Comment> comments = new ArrayList<>();

    @CreatedDate
    private LocalDateTime createdDate;
}
