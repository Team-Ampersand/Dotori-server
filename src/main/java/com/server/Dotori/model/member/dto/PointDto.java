package com.server.Dotori.model.member.dto;

import lombok.*;

@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED) @AllArgsConstructor
public class PointDto {

    private Long receiverId;
    private Long point;
}
