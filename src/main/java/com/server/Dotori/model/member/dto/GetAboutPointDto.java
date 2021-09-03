package com.server.Dotori.model.member.dto;

import lombok.*;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class GetAboutPointDto {

    private Long id;
    private String stuNum;
    private String username;
    private Long point;
}
