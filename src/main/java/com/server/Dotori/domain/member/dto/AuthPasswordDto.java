package com.server.Dotori.domain.member.dto;

import lombok.Getter;

import javax.validation.constraints.Pattern;

@Getter
public class AuthPasswordDto {
    @Pattern(regexp = "^[a-zA-Z0-9]+@gsm.hs.kr$")
    private String email;
    private String answer;
}
