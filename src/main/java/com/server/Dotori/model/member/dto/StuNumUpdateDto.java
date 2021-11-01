package com.server.Dotori.model.member.dto;

import lombok.*;

@Getter @Builder
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StuNumUpdateDto {

    private Long receiverId;
    private String stuNum;
}
