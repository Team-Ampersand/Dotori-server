package com.server.Dotori.model.member.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SelfStudyStudentsDto {

    private Long id;
    private String stdNum;
    private String username;

    public SelfStudyStudentsDto(Long id, String stdNum, String username) {
        this.id = id;
        this.stdNum = stdNum;
        this.username = username;
    }
}
