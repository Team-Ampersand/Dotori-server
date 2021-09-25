package com.server.Dotori.model.member.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
public class MemberPasswordDto {
    @Size(min = 4)
    private String oldPassword;
    @Size(min = 4)
    private String newPassword;
}
