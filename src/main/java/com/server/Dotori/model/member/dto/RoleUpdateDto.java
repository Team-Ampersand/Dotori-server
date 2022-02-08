package com.server.Dotori.model.member.dto;

import com.server.Dotori.model.member.enumType.Role;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter @Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED) @NoArgsConstructor
public class RoleUpdateDto {

    @NotBlank
    private Long receiverId;

    @NotBlank
    private List<Role> roles;
}
