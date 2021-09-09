package com.server.Dotori.model.member.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class MemberLoginDto {
    @NotBlank(message = "email should be valid")
    @Size(min = 1, max = 16)
    private String email;

    @NotBlank(message = "password should be valid")
    @Size(min = 4)
    private String password;
    
}
