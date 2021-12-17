package com.server.Dotori.model.member.dto;

import lombok.Getter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
public class VerifiedAuthKeyAndChangePasswordDto {

    @Size(min = 6, max = 6)
    String key;

    @Size(min = 4)
    String newPassword;
}
