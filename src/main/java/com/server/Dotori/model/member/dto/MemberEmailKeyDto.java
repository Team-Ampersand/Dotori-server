package com.server.Dotori.model.member.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
public class MemberEmailKeyDto {
    @Size(min = 6, max = 6)
    private String key;
}
