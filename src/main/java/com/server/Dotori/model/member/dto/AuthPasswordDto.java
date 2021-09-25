package com.server.Dotori.model.member.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@Getter
@Setter
public class AuthPasswordDto {
    @Pattern(regexp = "^[a-zA-Z0-9]+@(g)(s)(m)(.)(h)(s)(.)(k)(r)$")
    private String email;
    private String answer;
}
