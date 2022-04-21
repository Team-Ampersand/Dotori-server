package com.server.Dotori.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SignUpEmailCheckDto {
    @Size(min = 6, max = 6)
    private String key;
}
