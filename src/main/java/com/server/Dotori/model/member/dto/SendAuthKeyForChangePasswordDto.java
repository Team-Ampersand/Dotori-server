package com.server.Dotori.model.member.dto;

import lombok.Getter;

import javax.validation.constraints.Pattern;

@Getter
public class SendAuthKeyForChangePasswordDto {

    @Pattern(regexp = "^[a-zA-Z0-9]+@gsm.hs.kr$")
    String email;
}
