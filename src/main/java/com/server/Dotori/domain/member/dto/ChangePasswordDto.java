package com.server.Dotori.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ChangePasswordDto {
    @NotBlank
    @Size(min = 4)
    private String currentPassword;

    @NotBlank
    @Size(min = 4)
    private String newPassword;
}
