package com.server.Dotori.model.member.dto;

import com.server.Dotori.model.member.Member;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class SignInDto {
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]+@gsm.hs.kr$")
    private String email;

    @NotBlank
    @Size(min = 4)
    private String password;

    @Builder
    public SignInDto(String email, String password){
        this.email = email;
        this.password = password;
    }
}
