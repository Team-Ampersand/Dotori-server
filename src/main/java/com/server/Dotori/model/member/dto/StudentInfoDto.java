package com.server.Dotori.model.member.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.server.Dotori.model.member.enumType.Role;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor @AllArgsConstructor
public class StudentInfoDto {

    private Long id;
    private String stdNum;
    private String username;
    private List<Role> roles;
}
