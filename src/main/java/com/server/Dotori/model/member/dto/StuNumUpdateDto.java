package com.server.Dotori.model.member.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter @Builder
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StuNumUpdateDto {

    @NotBlank
    private Long receiverId;

    @NotBlank
    private String stuNum;
}
