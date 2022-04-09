package com.server.Dotori.domain.member.dto;

import com.server.Dotori.domain.member.Member;
import com.server.Dotori.domain.member.enumType.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collections;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDto {
    @NotBlank
    @Size(min = 1, max = 10)
    private String memberName;

    @NotBlank
    @Size(min = 4, max = 4)
    private String stuNum;

    @NotBlank
    @Size(min = 4)
    private String password;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]+@gsm.hs.kr$")
    private String email;

    @NotBlank
    private Gender gender;

    public Member toEntity(String encodePassword, Gender gender){
        return Member.builder()
                .memberName(memberName)
                .stuNum(stuNum)
                .password(encodePassword)
                .email(email)
                .refreshToken(null)
                .roles(Collections.singletonList(Role.ROLE_MEMBER))
                .massage(Massage.CAN)
                .music(Music.CAN)
                .selfStudy(SelfStudy.CAN)
                .point(0L)
                .gender(gender)
                .build();
    }
}
