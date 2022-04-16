package com.server.Dotori.domain.self_study.dto;

import com.server.Dotori.domain.member.enumType.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SelfStudyStudentsDto {

    private Long id;
    private String stuNum;
    private String memberName;
    private Gender gender;
}
