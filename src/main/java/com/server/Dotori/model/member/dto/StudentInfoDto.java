package com.server.Dotori.model.member.dto;

import com.server.Dotori.model.member.enumType.Role;
import com.server.Dotori.model.member.enumType.SelfStudy;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED) @AllArgsConstructor
public class StudentInfoDto {

    private Long id;
    private String stuNum;
    private String memberName;
    private List<Role> roles;
    private SelfStudy selfStudy;
}
