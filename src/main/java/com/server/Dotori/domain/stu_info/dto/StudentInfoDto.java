package com.server.Dotori.domain.stu_info.dto;

import com.server.Dotori.domain.member.enumType.Gender;
import com.server.Dotori.domain.member.enumType.Role;
import com.server.Dotori.domain.member.enumType.SelfStudyStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED) @AllArgsConstructor
public class StudentInfoDto {

    private Long id;
    private String stuNum;
    private String memberName;
    private List<Role> roles;
    private SelfStudyStatus selfStudyStatus;
    private Gender gender;
}
