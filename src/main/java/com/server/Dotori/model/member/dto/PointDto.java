package com.server.Dotori.model.member.dto;

import lombok.*;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class PointDto {

    private Long receiverId;
    private Long point;
}
