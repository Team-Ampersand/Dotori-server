package com.server.Dotori.model.member.dto;

import com.server.Dotori.model.member.enumType.SelfStudy;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SelfStudyStudentsDto {

    private String stdNum;
    private String username;

    public SelfStudyStudentsDto(String stdNum, String username) {
        this.stdNum = stdNum;
        this.username = username;
    }
}
