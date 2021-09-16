package com.server.Dotori.model.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthPasswordDto {
    private String email;
    private String answer;
}
