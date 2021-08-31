package com.server.Dotori.model.member.dto.point;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class PointDto {

    private Long receiverId;
    private Long point;
}
