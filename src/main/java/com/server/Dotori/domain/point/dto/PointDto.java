package com.server.Dotori.domain.point.dto;

import lombok.*;

@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED) @AllArgsConstructor
public class PointDto {

    private Long receiverId;
    private Long point;
}
