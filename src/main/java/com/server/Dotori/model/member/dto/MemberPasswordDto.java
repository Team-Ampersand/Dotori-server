package com.server.Dotori.model.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberPasswordDto {
    private String oldPassword;
    private String newPassword;
}
