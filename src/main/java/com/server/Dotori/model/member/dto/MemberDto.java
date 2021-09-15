package com.server.Dotori.model.member.dto;

import com.server.Dotori.model.member.Member;
import com.server.Dotori.model.member.enumType.Music;
import com.server.Dotori.model.member.enumType.Role;
import com.server.Dotori.model.member.enumType.SelfStudy;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collections;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDto {
    @NotBlank(message = "username should be valid")
    @Size(min = 1, max = 10)
    private String username;

    @NotBlank(message = "stdNum should be valid")
    @Size(min = 4, max = 4)
    private String stdNum;

    @NotBlank(message = "password should be valid")
    @Size(min = 4)
    private String password;

    @NotBlank(message = "email should be valid")
    private String email;

    @NotBlank(message = "answer should be valid")
    private String answer;

    public Member toEntity(){
        return Member.builder()
                .username(username)
                .stdNum(stdNum)
                .password(password)
                .email(email)
                .roles(Collections.singletonList(Role.ROLE_MEMBER))
                .music(Music.CAN)
                .selfStudy(SelfStudy.CAN)
                .point(0L)
                .answer(answer)
                .build();
    }
}
