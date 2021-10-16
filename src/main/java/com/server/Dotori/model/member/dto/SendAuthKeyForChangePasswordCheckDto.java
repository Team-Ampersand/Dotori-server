package com.server.Dotori.model.member.dto;

import lombok.Getter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
public class SendAuthKeyForChangePasswordCheckDto {

    @Pattern(regexp = "^[a-zA-Z0-9]+@gsm.hs.kr$")
    String email;

    @Size(min = 0, max = 6)
    String key;

    @Size(min = 4)
    String newPassword;
}
