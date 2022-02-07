package com.server.Dotori.model.member.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter @Setter @Builder
@AllArgsConstructor @NoArgsConstructor
public class MemberNameUpdateDto {

    @NotBlank
    private Long receiverId;

    @NotBlank
    private String memberName;
}
