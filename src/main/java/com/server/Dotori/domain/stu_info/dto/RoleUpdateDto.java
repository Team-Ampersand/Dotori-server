package com.server.Dotori.domain.stu_info.dto;

import com.server.Dotori.domain.member.enumType.Role;
import lombok.*;

import java.util.List;

@Getter @Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED) @NoArgsConstructor
public class RoleUpdateDto {

    private Long receiverId;

    private List<Role> roles;
}
