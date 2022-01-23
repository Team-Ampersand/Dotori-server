package com.server.Dotori.model.member.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
public class ChangePasswordDto {
    @NotBlank
    @Size(min = 4)
    private String currentPassword;

    @NotBlank
    @Size(min = 4)
    private String newPassword;
}
