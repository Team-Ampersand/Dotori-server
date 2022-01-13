package com.server.Dotori.model.member.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SelfStudyStudentsDto {

    private Long id;
    private String stuNum;
    private String memberName;

    public SelfStudyStudentsDto(Long id, String stuNum, String memberName) {
        this.id = id;
        this.stuNum = stuNum;
        this.memberName = memberName;
    }
}
