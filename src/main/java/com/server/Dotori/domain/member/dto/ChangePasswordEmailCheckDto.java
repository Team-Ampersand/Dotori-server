package com.server.Dotori.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ChangePasswordEmailCheckDto {

    @Size(min = 6, max = 6)
    private String key;

    @Size(min = 4)
    private String newPassword;
}
