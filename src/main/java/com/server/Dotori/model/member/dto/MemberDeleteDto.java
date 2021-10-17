package com.server.Dotori.model.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDeleteDto {
    private String email;
    private String password;
}
