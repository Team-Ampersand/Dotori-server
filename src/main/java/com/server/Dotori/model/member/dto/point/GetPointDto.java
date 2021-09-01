package com.server.Dotori.model.member.dto.point;

import lombok.*;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class GetPointDto {

    private Long id;
    private String stuNum;
    private String username;
    private Long point;
}
