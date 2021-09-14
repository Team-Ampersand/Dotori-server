package com.server.Dotori.model.member.dto;

import com.server.Dotori.model.member.enumType.Role;
import lombok.*;

import java.util.List;

@Getter @Setter @Builder
@AllArgsConstructor @NoArgsConstructor
public class RoleUpdateDto {

    private Long receiverId;
    private List<Role> roles;
}
