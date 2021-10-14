package com.server.Dotori.model.member.dto;

import lombok.Getter;

import javax.validation.constraints.Size;

@Getter
public class BeforeLoginPasswordChangeCheckDto {
    @Size(min = 0, max = 6)
    String key;

    String newPassword;
}
