package com.server.Dotori.domain.member.dto;

import com.server.Dotori.domain.member.enumType.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class SignInResponseDto {

    private Map<String, String> token;
    private Gender gender;
}
