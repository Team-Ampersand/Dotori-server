package com.server.Dotori.model.member.dto;

import lombok.*;

@Getter @Setter @Builder
@AllArgsConstructor @NoArgsConstructor
public class MemberNameUpdateDto {

    private Long receiverId;

    private String memberName;
}
