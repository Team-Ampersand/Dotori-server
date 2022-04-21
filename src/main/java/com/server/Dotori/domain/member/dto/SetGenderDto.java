package com.server.Dotori.domain.member.dto;

import com.server.Dotori.domain.member.enumType.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SetGenderDto {
    private String email;
    private Gender gender;
}
