package com.server.Dotori.domain.point.dto;

import lombok.*;

@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED) @AllArgsConstructor
public class GetAboutPointDto {

    private Long id;
    private String stuNum;
    private String memberName;
    private Long point;
}
