package com.server.Dotori.model.member.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class MemberLoginDto {
    @NotBlank(message = "username should be valid")
    @Size(min = 1, max = 10)
    private String username;

    @NotBlank(message = "password should be valid")
    @Size(min = 4)
    private String password;
    
}
