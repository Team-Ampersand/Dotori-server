package com.server.Dotori.model.member.dto;

import lombok.Getter;

import javax.validation.constraints.Size;

@Getter
public class ChangePasswordEmailCheckDto {

    @Size(min = 6, max = 6)
    private String key;

    @Size(min = 4)
    private String newPassword;
}
