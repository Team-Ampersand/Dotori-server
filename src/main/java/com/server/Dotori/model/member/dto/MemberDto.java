package com.server.Dotori.model.member.dto;

import com.server.Dotori.model.member.Member;
import com.server.Dotori.model.member.enumType.Music;
import com.server.Dotori.model.member.enumType.Role;
import com.server.Dotori.model.member.enumType.SelfStudy;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collections;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDto {
    @NotBlank
    @Size(min = 1, max = 10)
    private String username;

    @NotBlank
    @Size(min = 4, max = 4)
    private String stdNum;

    @NotBlank
    @Size(min = 4)
    private String password;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]+@gsm.hs.kr$")
    private String email;

    public Member toEntity(){
        return Member.builder()
                .username(username)
                .stdNum(stdNum)
                .password(password)
                .email(email)
                .refreshToken(null)
                .roles(Collections.singletonList(Role.ROLE_MEMBER))
                .music(Music.CAN)
                .selfStudy(SelfStudy.CAN)
                .point(0L)
                .build();
    }
}
